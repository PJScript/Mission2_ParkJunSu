package com.example.storeweb.exception;

import com.example.storeweb.common.dto.BaseResponseDto;
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
        } catch (JwtException ex) {
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
                BaseResponseDto.builder()
                        .status(status.value())
                        .error(errorCode)
                        .message(ex.getMessage())
                        .path(request.getServletPath())
                        .timestamp(LocalDateTime.now().toString())
                        .build()
                        .convertToJson()
        );
    }
}

