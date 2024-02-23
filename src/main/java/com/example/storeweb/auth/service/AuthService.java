package com.example.storeweb.auth.service;

import com.example.storeweb.auth.dto.LoginRequestDto;
import com.example.storeweb.auth.dto.TenantDto;
import com.example.storeweb.auth.entity.TenantEntity;
import com.example.storeweb.auth.repo.TenantRepository;
import com.example.storeweb.utils.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements AuthServiceImpl{
    private final TenantRepository tenantRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public String login(
        LoginRequestDto dto
    ) {
        String account = dto.getAccount();
        String password = dto.getPassword();

        TenantEntity tenant = tenantRepository.findTenantEntityByAccount(account);
        if(tenant == null){
            log.debug("존재하지 않는 유저");
        }

        if(!password.equals(tenant.getPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        TenantDto info = new TenantDto(tenant.getUuid());
        String accessToken = jwtUtil.createAccessToken(info);

        log.debug("accessToken: ", accessToken);


        return accessToken;
    }
}
