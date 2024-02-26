package com.example.storeweb.domain.auth.service;

import com.example.storeweb.domain.auth.dto.LoginRequestDto;
import com.example.storeweb.domain.auth.dto.UserInfoDto;
import com.example.storeweb.common.dto.BaseResponseDto;
import com.example.storeweb.utils.dto.TokenInfoDto;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthServiceImpl {

  TokenInfoDto login(LoginRequestDto dto, HttpServletRequest req);
  UserInfoDto getUserInfo(String jwt, HttpServletRequest req);

}
