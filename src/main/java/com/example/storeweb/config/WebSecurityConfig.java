package com.example.storeweb.config;


import com.example.storeweb.exception.ExceptionHandlerFilter;
import com.example.storeweb.jwt.JwtTokenFilter;
import com.example.storeweb.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtUtil jwtUtil;
    private final UserDetailsManager manager;
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception{
        http
                // csrf 보안 해제
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JwtTokenFilter(jwtUtil,manager), AuthorizationFilter.class)
                .addFilterBefore(new ExceptionHandlerFilter(), JwtTokenFilter.class);

        return http.build();
    }


}
