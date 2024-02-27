package com.example.storeweb.common.security;


import com.example.storeweb.domain.auth.service.CustomUserDetailsService;
import com.example.storeweb.exception.CustomException;
import com.example.storeweb.exception.CustomJwtException;
import com.example.storeweb.exception.GlobalException;
import com.example.storeweb.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtUtil JwtUtil;
    // 사용자 정보를 찾기위한 UserDetailsService 또는 Manager
    private final CustomUserDetailsService customUserDetailsService;



    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // 검증이 필요 없는 엔드포인트를 기록
        List<String> list = List.of(
                "/v1/auth/join",
                "/v1/auth/login",
                "/v1/auth/test"
        );

        log.info("필터 테스트");
        // 토큰이 필요하지 않은 API URL의 경우 -> 로직 처리없이 다음 필터로 이동한다.
        if (list.contains(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        log.info("JWT 필터 미검증 엔드포인트 넘어갔는지 확인");


        // Authorization 헤더가 없다면 Exception 발생
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null) {
           throw new CustomJwtException(GlobalException.TOKEN_NOT_FOUND);
        }

        // Authorization 헤더가 올바르지 않으면 Exception 발생
        if(!authHeader.startsWith("Bearer ")){
            throw new CustomJwtException(GlobalException.TOKEN_NOT_FOUND);
        }

        String token = authHeader.split(" ")[1];

        log.info(token + ": 토큰");
        if(token.isEmpty()){
            throw new CustomJwtException(GlobalException.TOKEN_NOT_FOUND);
        }


        if (JwtUtil.validateToken(token)) {
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            String uuid = JwtUtil.getUserUuId(token);

            log.info(uuid + "UUID");

            UserDetails userDetails = customUserDetailsService.loadUserByTenantUuid(uuid);
            for (GrantedAuthority authority :userDetails.getAuthorities()) {
                log.info("authority: {}", authority.getAuthority());
            }

            // 인증 정보 생성
            AbstractAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            token,
                            userDetails.getAuthorities()
                    );
            // 인증 정보 등록
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);

        } else {
            log.warn("jwt validation failed");

            throw new CustomJwtException(GlobalException.TOKEN_EXPIRED);
        }
        // 5. 다음 필터 호출
        // doFilter를 호출하지 않으면 Controller까지 요청이 도달하지 못한다.
        filterChain.doFilter(request, response);
    }
}


