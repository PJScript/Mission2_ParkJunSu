package com.example.storeweb.exception;

import com.example.storeweb.common.GlobalSystemStatus;
import com.example.storeweb.common.dto.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BaseResponse> handleCustomException(CustomException ex, HttpServletRequest req) {
        GlobalSystemStatus globalException = ex.getGlobalException();

        BaseResponse response = BaseResponse.builder()
                .systemMessage(globalException.getSystemMessage())
                .systemCode(globalException.getSystemCode())
                .build();

        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );


    }
}
