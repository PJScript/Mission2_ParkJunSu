package com.example.storeweb.common.security;

import com.example.storeweb.common.service.SecurityService;
import com.example.storeweb.domain.auth.entity.ActivityEntity;
import com.example.storeweb.domain.auth.entity.RoleEntity;
import com.example.storeweb.domain.auth.repo.ActivityRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Slf4j
public class DynamicUrlFilter extends OncePerRequestFilter {
    private final ActivityRepository activityRepository;
    private final SecurityService securityService;

    public DynamicUrlFilter(ActivityRepository activityRepository, SecurityService securityService) {
        this.activityRepository = activityRepository;
        this.securityService = securityService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestUrl = request.getRequestURI();
        List<ActivityEntity> activities = securityService.getActivitiesByUrlPattern(requestUrl);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Collection<? extends GrantedAuthority> userAuthorities = authentication.getAuthorities();
            boolean isAuthorized = activities.stream()
                    .map(ActivityEntity::getRole)
                    .map(RoleEntity::getValue) // RoleEntity의 권한 값을 가져오기
                    .anyMatch(roleValue -> userAuthorities.contains(new SimpleGrantedAuthority(roleValue)));



            userAuthorities.forEach(auth -> log.info("User Authority: {}", auth.getAuthority()));
            activities.forEach(activity -> log.info("Required Authority: {}", activity.getRole().getValue()));

            log.info("Dynamic Filter 테스트");
            if (!isAuthorized) {
                log.info("접근 거부");
                response.sendError(HttpStatus.FORBIDDEN.value(), "Access Denied");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}

