package com.example.storeweb.exception;

import io.jsonwebtoken.JwtException;
import lombok.Getter;


@Getter
public class CustomException extends RuntimeException {

    private final GlobalException globalException;

    public CustomException(GlobalException globalException) {
        super(globalException.getMessage());
        this.globalException = globalException;
    }

}
