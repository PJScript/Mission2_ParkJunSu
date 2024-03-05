package com.example.storeweb.exception;


import com.example.storeweb.common.GlobalSystemStatus;
import lombok.Getter;


@Getter
public class CustomException extends RuntimeException {

    private final GlobalSystemStatus globalException;

    public CustomException(GlobalSystemStatus globalException) {
        super(globalException.getSystemMessage());
        this.globalException = globalException;
    }



}