package com.example.storeweb.common.security;
import com.example.storeweb.domain.auth.entity.ActivityEntity;
import com.example.storeweb.domain.auth.repo.ActivityRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;




@Slf4j
@AllArgsConstructor
public class DynamicUrlFilter extends OncePerRequestFilter {

    private final ActivityRepository activityRepository;
    private Map<String, Set<String>> urlRoleMappings;
    private final List<Pattern> permitAllPatterns;


    public DynamicUrlFilter(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
        initializeUrlRoleMappings();
        permitAllPatterns = NoFilterUrlPattern.PERMIT_ALL_URL_PATTERNS_02.stream()
                .map(Pattern::compile)
                .collect(Collectors.toList());
    }

    private void initializeUrlRoleMappings() {
        urlRoleMappings = new HashMap<>();
        List<ActivityEntity> activities = activityRepository.findAll();
        for (ActivityEntity activity : activities) {
            String roleValue = activity.getRole().getValue();
            String regex = activity.getUrlPattern();
            urlRoleMappings.computeIfAbsent(regex, k -> new HashSet<>())
                    .add(roleValue);
        }
    }

    private Set<String> getMatchingRoles(String requestUrl) {
        return urlRoleMappings.entrySet().stream()
                .filter(entry -> requestUrl.matches(entry.getKey()))
                .flatMap(entry -> entry.getValue().stream())
                .collect(Collectors.toSet());
    }



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestUrl = request.getRequestURI();

        // 인증이 필요 없는 URL 패턴 확인
        boolean isPermitAll = permitAllPatterns.stream()
                .anyMatch(pattern -> pattern.matcher(requestUrl).matches());

        if (isPermitAll) {
            filterChain.doFilter(request, response);
            return;
        }


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        if (authentication == null || !authentication.isAuthenticated()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication is required");
            return;
        }

        Set<String> userAuthorities = getUserAuthorities(authentication);
        Set<String> requiredRoles = getMatchingRoles(requestUrl);
        boolean isUrlMapped = !requiredRoles.isEmpty();
        boolean isAuthorized = isUrlMapped && (requiredRoles.stream().anyMatch(userAuthorities::contains));

        if (!isAuthorized) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private Set<String> getUserAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
    }
}




