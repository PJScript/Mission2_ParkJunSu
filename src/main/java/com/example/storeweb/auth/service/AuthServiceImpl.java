package com.example.storeweb.auth.service;

import com.example.storeweb.auth.dto.LoginRequestDto;
import com.example.storeweb.auth.dto.LoginResponseDto;
import com.example.storeweb.common.dto.BaseResponseDto;
import com.example.storeweb.utils.dto.TokenInfoDto;

public interface AuthServiceImpl {

  BaseResponseDto<TokenInfoDto> login(LoginRequestDto dto);

}
