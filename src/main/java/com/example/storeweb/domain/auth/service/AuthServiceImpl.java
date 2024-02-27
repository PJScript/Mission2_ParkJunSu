package com.example.storeweb.domain.auth.service;

import com.example.storeweb.domain.auth.dto.LoginRequestDto;
import com.example.storeweb.domain.auth.dto.TenantDto;
import com.example.storeweb.utils.dto.TokenInfoDto;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthServiceImpl {

  TokenInfoDto login(LoginRequestDto dto, HttpServletRequest req);
  TenantDto.UserInfoDto getUserInfo(String jwt, HttpServletRequest req);
  boolean createPreActiveTenant(TenantDto.JoinRequestDto dto);
  TenantDto.UserInfoDto modifyTenant(TenantDto.UserInfoDto dto);

}
