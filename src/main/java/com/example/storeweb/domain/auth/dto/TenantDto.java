package com.example.storeweb.domain.auth.dto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestBody;

@Getter
@AllArgsConstructor
public class TenantDto {
    private String uuid;


    @Getter
    public static class JoinRequestDto {
        private String account;
        private String password;
    }

    public static class JoinResponseDto {

    }
}
