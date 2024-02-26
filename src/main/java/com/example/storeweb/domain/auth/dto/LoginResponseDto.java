package com.example.storeweb.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * <p>
 * accessToken : 엑세스 토큰 <br />
 * createDate : 토큰 발급일 <br />
 * exprieDate : 토큰 만료일 <br />
 * expireTimeSecondFormat : 토큰 유지시간을 초로 나타낸 값
 * </p>
 * */

@Getter
@Setter
@AllArgsConstructor
public class LoginResponseDto {
    private String accessToken;
    private LocalDateTime createDate;
    private Long expireTimeSecondFormat;
    private LocalDateTime expireDate;
}
