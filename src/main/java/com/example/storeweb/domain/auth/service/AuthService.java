package com.example.storeweb.domain.auth.service;

import com.example.storeweb.domain.auth.dto.LoginRequestDto;
import com.example.storeweb.domain.auth.dto.TenantDto;
import com.example.storeweb.domain.auth.dto.UserInfoDto;
import com.example.storeweb.domain.auth.entity.TenantEntity;
import com.example.storeweb.domain.auth.repo.TenantRepository;
import com.example.storeweb.common.dto.BaseResponseDto;
import com.example.storeweb.utils.JwtUtil;
import com.example.storeweb.utils.dto.TokenInfoDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements AuthServiceImpl{
    private final TenantRepository tenantRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public TokenInfoDto login(
        LoginRequestDto dto,
        HttpServletRequest req
    ) {
        String account = dto.getAccount();
        String password = dto.getPassword();

        TenantEntity tenant = tenantRepository.findTenantEntityByAccount(account);
        if(tenant == null){
            log.debug("존재하지 않는 유저");
        }

        if(!password.equals(tenant.getPassword())){
            return null;
        }

        TenantDto info = new TenantDto(tenant.getUuid());
        TokenInfoDto tokenInfo = jwtUtil.createAccessToken(info);


        return new TokenInfoDto(
                tokenInfo.getAccessToken(),
                tokenInfo.getCreateDate(),
                tokenInfo.getExpireDate(),
                tokenInfo.getExpireTimeSecondFormat()
        );
    }

    @Transactional
    public UserInfoDto getUserInfo(String jwt, HttpServletRequest req){
        System.out.println(jwtUtil.validateToken(jwt.split(" ")[1]) + " Validate");

        // Bearer 접두사를 제외한 순수 토큰 분리
        String token = jwt.split(" ")[1];
        String tokenUuid = "";

        // 토큰이 유효하면 해당 토큰에 들어있는 UUID 추출
        if(jwtUtil.validateToken(token)){
            tokenUuid = jwtUtil.getUserUuId(token);
            System.out.println("Token UUID: " + tokenUuid);
        }

        Optional<TenantEntity> tenant = tenantRepository.findTenantEntityByUuid(tokenUuid);
        return tenant.map(tenantEntity -> UserInfoDto.builder()
                .uuid(tenantEntity.getUuid())
                .account(tenantEntity.getAccount())
                .address(tenantEntity.getAddress())
                .addressDetail(tenantEntity.getAddressDetail())
                .age(tenantEntity.getAge())
                .email(tenantEntity.getEmail())
                .name(tenantEntity.getName())
                .nickname(tenantEntity.getNickname())
                .phone1(tenantEntity.getPhone1())
                .phone2(tenantEntity.getPhone2())
                .profileImageUrl(tenantEntity.getProfileImageUrl())
                .privinceLevelDivision(tenantEntity.getPrivinceLevelDivision())
                .municipalLevelDivision(tenantEntity.getMunicipalLevelDivision())
                .subMunicipalLevelDivision(tenantEntity.getSubMunicipalLevelDivision())
                .build()).orElse(null);


//        System.out.println(tenant.toString());


    }
}
