package com.example.storeweb.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GlobalException {

    TOKEN_EXPIRED(401, "T0001","토큰이 만료되었습니다",""),
    TOKEN_NOT_FOUND(401,"T0002","토큰이 없습니다.",""),
    ACCESS_DENIED(403,"A0001","접근 권한이 없습니다.",""),
    BAD_REQUEST(400,"A0002","인자를 올바르게 입력 해주세요",""),
    DUPLICATE_ACCOUNT(400,"A0003","존재하는 아이디 입니다.","");



    private final int status;
    private final String code;
    private final String message;
    private final Object data;


}