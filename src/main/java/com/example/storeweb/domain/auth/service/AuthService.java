package com.example.storeweb.domain.auth.service;

import com.example.storeweb.domain.auth.dto.LoginRequestDto;
import com.example.storeweb.domain.auth.dto.TenantDto;
import com.example.storeweb.domain.auth.entity.RoleEntity;
import com.example.storeweb.domain.auth.entity.TenantEntity;
import com.example.storeweb.domain.auth.repo.RoleRepository;
import com.example.storeweb.domain.auth.repo.TenantAccountGeneralRepository;
import com.example.storeweb.domain.auth.repo.TenantRepository;
import com.example.storeweb.exception.CustomException;
import com.example.storeweb.common.GlobalSystemStatus;
import com.example.storeweb.utils.JwtUtil;
import com.example.storeweb.utils.PasswordEncoderUtil;
import com.example.storeweb.utils.SecurityUtil;
import com.example.storeweb.utils.dto.TokenInfoDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements AuthServiceImpl {
    private final TenantRepository tenantRepository;
    private final RoleRepository roleRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoderUtil passwordEncoderUtil;

    public TokenInfoDto login(
            LoginRequestDto dto,
            HttpServletRequest req
    ) {
        String account = dto.getAccount();
        String password = dto.getPassword();


        TenantEntity tenant = tenantRepository.findTenantEntityByAccount(account)
                .orElseThrow(() -> new CustomException(GlobalSystemStatus.BAD_REQUEST));

        log.info("에러발생 여부");
        if (
                passwordEncoderUtil.passwordEncoder().encode(password).equals(tenant.getPassword())
        ) {
            throw new CustomException(GlobalSystemStatus.PASSWORD_OR_ACCOUNT_NOT_FOUND);
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
    public TenantEntity getUserInfo(String jwt) {

        // Bearer 접두사를 제외한 순수 토큰 분리
        String token = jwt.split(" ")[1];
        String tokenUuid = "";

        // 토큰이 유효하면 해당 토큰에 들어있는 UUID 추출
        if (jwtUtil.validateToken(token)) {
            tokenUuid = jwtUtil.getUserUuId(token);
        }

        return tenantRepository.findTenantEntityByUuid(tokenUuid).orElseThrow();
    }


    /**
     * <p>비활성 사용자로 가입 </p>
     * <p>
     * 회원가입 요청마다 현재 DB에 있는 ROLE 체크 후 비활성 사용자 ROLE 부여
     * </p>
     */
    @Transactional
    public boolean createPreActiveTenant(TenantDto.JoinRequestDto dto) {
        RoleEntity role = roleRepository.findRoleEntityByValue("ROLE_TENANT_PREACTIVE");

        TenantEntity tenant = TenantEntity.builder()
                .account(dto.getAccount())
                .uuid(UUID.randomUUID().toString())
                .role(role)
                .password(passwordEncoderUtil.passwordEncoder().encode(dto.getPassword()) )
                .build();


        tenantRepository.save(tenant);


        return true;
    }

    /**
     * 유저 정보 수정 메서드
     * <p>현재 사용자가 비활성인지, 활성인지 확인 후 조건 충족하면 권한 필드도 변경해줘야합니다.
     * </p>
     */
    @Transactional
    public TenantEntity modifyTenant(TenantDto.UserInfoDto dto) {
        UserDetails userDetails = SecurityUtil.getCurrentUserDetails();

        if (!userDetails.getUsername().equals(dto.getUuid())) {
            throw new CustomException(GlobalSystemStatus.ACCESS_DENIED);
        }

        TenantEntity tenant = tenantRepository.findTenantEntityByUuid(dto.getUuid())
                .orElseThrow(() -> new EntityNotFoundException("Tenant not found with UUID: " + dto.getUuid()));

        log.info(tenant.getId() + " : ID");
        TenantEntity updateTenant = TenantDto.UserInfoDto.dtoToEntity(dto, tenant);
        updateRole(updateTenant);

        return tenantRepository.save(updateTenant);

    }

    /**
     * <p>
     * Entity를 직접 받아 값을 확인 후 권한을 변경해주는 메서드 입니다. <br />
     * </p>
     */
    public void updateRole(TenantEntity tenant) {
        log.info(tenant.getName() + " : 이름");
        log.info(tenant.getNickname() + " : 닉네임");
        log.info(tenant.getAge() + " : 연령대");
        log.info(tenant.getEmail() + " : 이메일");
        log.info(tenant.getPhone1() + " : 휴대폰");
        RoleEntity updatedRoleTenant;
        // 활성 사용자로 변경하기 위한 모든 값이 존재한다면 활성사용자로 변경
        //닉네임, 이름, 연령대, 이메일, 전화번호
        if (
                !tenant.getNickname().isEmpty() ||
                        !tenant.getName().isEmpty() ||
                        !tenant.getAge().isEmpty() ||
                        !tenant.getEmail().isEmpty() ||
                        !tenant.getPhone1().isEmpty()
        ) {
            RoleEntity activeRole = roleRepository.findRoleEntityByValue("ROLE_TENANT_ACTIVE");
            log.info(activeRole.getValue() + " : 권한명");
            tenant.updateRole(activeRole);

        }
        ;


    }

    public boolean duplicateCheck(String account) {
        return tenantRepository.findByAccount(account).isPresent();

    }
}
