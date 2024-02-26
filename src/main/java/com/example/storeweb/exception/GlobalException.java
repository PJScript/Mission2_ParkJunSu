package com.example.storeweb.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GlobalException {

    TOKEN_EXPIRED(401, "TOKEN_EXPIRED","토큰이 만료되었습니다",""),
    TOKEN_NOT_FOUND(401,"TOKEN_NOT_FOUND","토큰이 없습니다.",""),
    ACCESS_DENIED(403,"ACCESS_DENIED","접근 권한이 없습니다.","");


    private final int status;
    private final String code;
    private final String message;
    private final String path;

}