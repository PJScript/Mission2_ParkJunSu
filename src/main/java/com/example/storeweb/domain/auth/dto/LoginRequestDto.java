package com.example.storeweb.domain.auth.dto;

import lombok.Getter;

@Getter
public class LoginRequestDto {
    String account;
    String password;
}

