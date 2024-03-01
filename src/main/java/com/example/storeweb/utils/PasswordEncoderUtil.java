package com.example.storeweb.utils;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderUtil {

    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();

    }
}
