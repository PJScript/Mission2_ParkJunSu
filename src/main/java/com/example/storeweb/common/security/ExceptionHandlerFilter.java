package com.example.storeweb.common.security;

import com.example.storeweb.common.dto.BaseResponse;
import com.example.storeweb.exception.CustomException;
import com.example.storeweb.exception.CustomJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (JwtException | CustomException ex) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, request, response, ex);
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletRequest request,
                                 HttpServletResponse response, Throwable ex) throws IOException {

        String errorCode = "";
        if (ex instanceof CustomJwtException) {
            errorCode = ((CustomJwtException) ex).getCode();
        }
        response.setContentType("application/json; charset=UTF-8");


        response.getWriter().write(
                BaseResponse.builder()
                        .systemMessage(ex.getMessage())
                        .build().toString()
        );
    }
}

