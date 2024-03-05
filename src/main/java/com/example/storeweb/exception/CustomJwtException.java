package com.example.storeweb.exception;

import com.example.storeweb.common.GlobalSystemStatus;
import io.jsonwebtoken.JwtException;
import lombok.Getter;


@Getter
public class CustomJwtException extends JwtException {

    private final GlobalSystemStatus globalException;

    public CustomJwtException(GlobalSystemStatus globalException) {
        super(globalException.getSystemMessage());
        this.globalException = globalException;
    }

    // ExceptionHandlerFilter로 사전정의한 Exception Code를 전달하기 위한 메서드
    public String getCode() {
        return globalException.getSystemCode();
    }

}
