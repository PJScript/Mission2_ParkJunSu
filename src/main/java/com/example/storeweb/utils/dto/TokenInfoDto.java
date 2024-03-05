package com.example.storeweb.utils.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class TokenInfoDto {
    private String accessToken;
    private LocalDateTime createDate;
    private LocalDateTime expireDate;
    private Long expireTimeSecondFormat;
}
