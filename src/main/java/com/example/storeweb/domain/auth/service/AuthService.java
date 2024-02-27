package com.example.storeweb.domain.auth.service;

import com.example.storeweb.domain.auth.dto.LoginRequestDto;
import com.example.storeweb.domain.auth.dto.TenantDto;
import com.example.storeweb.domain.auth.entity.RoleEntity;
import com.example.storeweb.domain.auth.entity.TenantEntity;
import com.example.storeweb.domain.auth.repo.RoleRepository;
import com.example.storeweb.domain.auth.repo.TenantAccountGeneralRepository;
import com.example.storeweb.domain.auth.repo.TenantRepository;
import com.example.storeweb.exception.CustomException;
import com.example.storeweb.exception.GlobalException;
import com.example.storeweb.utils.JwtUtil;
import com.example.storeweb.utils.SecurityUtil;
import com.example.storeweb.utils.dto.TokenInfoDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements AuthServiceImpl {
    private final TenantRepository tenantRepository;
    private final RoleRepository roleRepository;
    private final TenantAccountGeneralRepository tenantAccountGeneralRepository;
    private final JwtUtil jwtUtil;
    private final SecurityUtil securityUtil;

    @Transactional
    public TokenInfoDto login(
            LoginRequestDto dto,
            HttpServletRequest req
    ) {
        String account = dto.getAccount();
        String password = dto.getPassword();

        TenantEntity tenant = tenantRepository.findTenantEntityByAccount(account);
        if (tenant == null) {
            log.debug("존재하지 않는 유저");
        }

        if (!password.equals(tenant.getPassword())) {
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
    public TenantDto.UserInfoDto getUserInfo(String jwt, HttpServletRequest req) {
        System.out.println(jwtUtil.validateToken(jwt.split(" ")[1]) + " Validate");

        // Bearer 접두사를 제외한 순수 토큰 분리
        String token = jwt.split(" ")[1];
        String tokenUuid = "";

        // 토큰이 유효하면 해당 토큰에 들어있는 UUID 추출
        if (jwtUtil.validateToken(token)) {
            tokenUuid = jwtUtil.getUserUuId(token);
            System.out.println("Token UUID: " + tokenUuid);
        }

        Optional<TenantEntity> tenant = tenantRepository.findByUuid(tokenUuid);
        return tenant.map(tenantEntity -> TenantDto.UserInfoDto.builder()
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


    /**
     * <p>
     * 비활성 사용자로 가입 <br />
     * 회원가입 요청마다 현재 DB에 있는 ROLE 체크 후 비활성 사용자 ROLE 부여
     * </p>
     */
    @Transactional
    public boolean createPreActiveTenant(TenantDto.JoinRequestDto dto) {
        RoleEntity role = roleRepository.findRoleEntityByValue("ROLE_TENANT_PREACTIVE");

        TenantEntity tenant = TenantEntity.builder()
                .account(dto.getAccount())
                .role(role)
                .password(dto.getPassword())
                .build();


        tenantRepository.save(tenant);


        return true;
    }

    /**
     * 유저 정보 수정 메서드 <br />
     * 현재 사용자가 비활성인지, 활성인지 확인 후 조건 충족하면 권한 필드도 변경해줘야함
     */

    @Transactional
    public TenantDto.UserInfoDto modifyTenant(TenantDto.UserInfoDto dto) {
        UserDetails userDetails = SecurityUtil.getCurrentUserDetails();

        log.info(userDetails.getUsername() + " : 유저네임");
        log.info(dto.getUuid() + " :  UUID");

        if (!userDetails.getUsername().equals(dto.getUuid())) {
            throw new CustomException(GlobalException.ACCESS_DENIED);
        }

        TenantEntity tenant = tenantRepository.findByUuid(dto.getUuid())
                .orElseThrow(() -> new EntityNotFoundException("Tenant not found with UUID: " + dto.getUuid()));

        log.info(tenant.getId() + " : ID");
        TenantEntity updateTenant = TenantDto.UserInfoDto.dtoToEntity(dto,tenant);
        tenantRepository.save(updateTenant);


        return TenantDto.UserInfoDto.entityToDto(updateTenant);

    }
}
