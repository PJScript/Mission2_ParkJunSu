package com.example.storeweb.domain.auth.service;

import com.example.storeweb.domain.auth.dto.LoginRequestDto;
import com.example.storeweb.domain.auth.dto.TenantDto;
import com.example.storeweb.domain.auth.entity.TenantEntity;
import com.example.storeweb.utils.dto.TokenInfoDto;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthServiceImpl {

  TokenInfoDto login(LoginRequestDto dto, HttpServletRequest req);
  TenantEntity getUserInfo(String jwt);
  boolean createPreActiveTenant(TenantDto.JoinRequestDto dto);
  TenantEntity modifyTenant(TenantDto.UserInfoDto dto);

}
