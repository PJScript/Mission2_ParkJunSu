package com.example.storeweb.exception;

import io.jsonwebtoken.JwtException;
import lombok.Getter;


@Getter
public class CustomJwtException extends JwtException {

    private final GlobalException globalException;

    public CustomJwtException(GlobalException globalException) {
        super(globalException.getMessage());
        this.globalException = globalException;
    }

    // ExceptionHandlerFilter로 사전정의한 Exception Code를 전달하기 위한 메서드
    public String getCode() {
        return globalException.getCode();
    }

}
