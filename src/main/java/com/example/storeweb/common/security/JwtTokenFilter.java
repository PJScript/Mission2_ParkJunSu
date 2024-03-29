package com.example.storeweb.common.security;


import com.example.storeweb.exception.CustomJwtException;
import com.example.storeweb.common.GlobalSystemStatus;
import com.example.storeweb.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtUtil JwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private List<Pattern> permitAllPatterns;



    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        permitAllPatterns = NoFilterUrlPattern.PERMIT_ALL_URL_PATTERNS.stream()
                .map(Pattern::compile)
                .collect(Collectors.toList());


        String requestUri = request.getRequestURI();
        boolean isPermitAll = permitAllPatterns.stream()
                .anyMatch(pattern -> pattern.matcher(requestUri).matches());

        if (isPermitAll) {
            filterChain.doFilter(request, response);
            return;
        }

        log.info("JWT 필터 미검증 엔드포인트 넘어갔는지 확인");


        // Authorization 헤더가 없다면 Exception 발생
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null) {
            log.info("토큰이 없음");
           throw new CustomJwtException(GlobalSystemStatus.TOKEN_NOT_FOUND);
        }

        // Authorization 헤더가 올바르지 않으면 Exception 발생
        if(!authHeader.startsWith("Bearer ")){
            log.info("토큰 앞부분이 올바르지 않음");
            throw new CustomJwtException(GlobalSystemStatus.TOKEN_NOT_FOUND);
        }

        String token = authHeader.split(" ")[1];

        log.info(token + ": 토큰");
        if(token.isEmpty()){
            throw new CustomJwtException(GlobalSystemStatus.TOKEN_NOT_FOUND);
        }


        if (JwtUtil.validateToken(token)) {
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            String uuid = JwtUtil.getUserUuId(token);

            log.info(uuid + "UUID");

            UserDetails userDetails = customUserDetailsService.loadUserByTenantUuid(uuid);
            for (GrantedAuthority authority :userDetails.getAuthorities()) {
                log.info("authority: {}", authority.getAuthority());
            }


            AbstractAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            token,
                            userDetails.getAuthorities()
                    );

            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);

        } else {
            log.warn("jwt validation failed");

            throw new CustomJwtException(GlobalSystemStatus.TOKEN_EXPIRED);
        }

        filterChain.doFilter(request, response);
    }
}


