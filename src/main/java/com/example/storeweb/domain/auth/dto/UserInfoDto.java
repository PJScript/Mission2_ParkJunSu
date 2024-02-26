package com.example.storeweb.domain.auth.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserInfoDto {
    private String uuid;
    private String account;
    private String address;
    private String addressDetail;
    private String age;
    private String email;
    private String name;
    private String nickname;
    private String phone1;
    private String phone2;
    private String profileImageUrl;
    private String privinceLevelDivision;
    private String municipalLevelDivision;
    private String subMunicipalLevelDivision;
}