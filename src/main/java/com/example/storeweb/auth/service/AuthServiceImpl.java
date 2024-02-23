package com.example.storeweb.auth.service;

import com.example.storeweb.auth.dto.LoginRequestDto;

public interface AuthServiceImpl {

  String login(LoginRequestDto dto);

}
