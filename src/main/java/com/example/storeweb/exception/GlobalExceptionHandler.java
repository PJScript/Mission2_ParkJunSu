package com.example.storeweb.exception;

import com.example.storeweb.common.dto.BaseResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BaseResponseDto<Object>> handleCustomException(CustomException ex, HttpServletRequest req) {
        GlobalException globalException = ex.getGlobalException();

        BaseResponseDto<Object> response = BaseResponseDto.builder()
                .status(globalException.getStatus())
                .message(globalException.getMessage())
                .data(globalException.getData())
                .timestamp(LocalDateTime.now().toString()
                ).build();

        return new ResponseEntity<>(response, HttpStatus.valueOf(globalException.getStatus()));
    }
}
