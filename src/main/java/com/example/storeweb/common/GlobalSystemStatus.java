package com.example.storeweb.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GlobalSystemStatus {
    SUCCESS_RESPONSE(HttpStatus.OK,"OK","Success"),
    // Exception


    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "T0001","토큰이 만료되었습니다"),
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED,"T0002","토큰이 없습니다."),
    ACCESS_DENIED(HttpStatus.UNAUTHORIZED,"A0003","접근 권한이 없습니다."),
    TOKEN_INVALID(HttpStatus.UNAUTHORIZED,"A0004","유효하지 않은 토큰혹은 유저 입니다."),

    BAD_REQUEST(HttpStatus.BAD_REQUEST,"A0001","인자를 올바르게 입력 해주세요"),
    BAD_REQUEST_ID(HttpStatus.BAD_REQUEST,"A0002","아이디를 올바르게 입력 해주세요"),
    DUPLICATE_ACCOUNT(HttpStatus.BAD_REQUEST,"A0003","존재하는 아이디 입니다."),
    PASSWORD_OR_ACCOUNT_NOT_FOUND(HttpStatus.BAD_REQUEST,"A0004","아이디 혹은 비밀번호가 올바르지 않습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "A0005","존재하지 않는 항목입니다."),
    TEST(HttpStatus.BAD_REQUEST,"TEST001","TEST001");


    private final HttpStatus status;
    private final String systemCode;
    private final String systemMessage;


}