package com.example.storeweb.domain.auth.controller;

import com.example.storeweb.domain.auth.dto.LoginRequestDto;
import com.example.storeweb.domain.auth.dto.TenantDto;
import com.example.storeweb.domain.auth.entity.TenantEntity;
import com.example.storeweb.domain.auth.service.AuthService;
import com.example.storeweb.common.dto.BaseResponseDto;
import com.example.storeweb.exception.CustomException;
import com.example.storeweb.exception.GlobalException;
import com.example.storeweb.utils.TimeUtil;

import com.example.storeweb.utils.dto.TokenInfoDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


/**
 * TenantController
 * <p>유저 정보에 관한 컨트롤러</p>
 *
 * @version v1
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final TimeUtil timeUtil;

    @GetMapping("/test")
    public String test() {
        log.debug("test");
        return "test";
    }

    /**
     * 로그인 엔드포인트
     */
    @PostMapping("/login")
    public BaseResponseDto<TokenInfoDto> login(
            @RequestBody
            LoginRequestDto dto,
            HttpServletRequest req
    ) {
        log.info("account:" + dto.getAccount());
        log.info("password: " + dto.getPassword());
        return BaseResponseDto.<TokenInfoDto>builder()
                .status(200)
                .message("Login Success")
                .data(authService.login(dto, req))
                .timestamp(LocalDateTime.now().toString())
                .build();
    }

    /**
     * 유저조회 엔드포인트
     *
     * <p>
     * JWT로 전달받은 <b>uuid</B> 를 통해 타겟 유저의 정보를 가져옵니다.
     *
     * </p>
     */
    @GetMapping("/user")
    public BaseResponseDto<TenantDto.UserInfoDto> getUser(
            @RequestHeader("Authorization")
            String jwt,
            HttpServletRequest req
    ) {
        log.info("HEADER: " + jwt);
        return BaseResponseDto.<TenantDto.UserInfoDto>builder()
                .status(200)
                .message("Login Success")
                .data(authService.getUserInfo(jwt, req))
                .timestamp(LocalDateTime.now().toString())
                .build();
    }

    /**
     * 회원가입 엔드포인트
     * <p>아이디, 비밀번호 두가지만으로 가입이 가능하다. <br />
     * 서비스를 이용하려면 닉네임,이름,연령대,이메일,전화번 정보를 추가해야합니다.</p>
     */
    @PostMapping("/join")
    public BaseResponseDto<String> join(
            @RequestBody
            TenantDto.JoinRequestDto dto
    ) {

        log.info(dto.getAccount() + " : account ----------");
        final TimeUtil timeUtil = new TimeUtil();
        if (dto.getAccount().isEmpty() || dto.getPassword().isEmpty()) {
            throw new CustomException(GlobalException.BAD_REQUEST);
        }

        // TODO: 아이디 조건 정규식으로 검증
        // TODO: 비밀번호 조건 정규식으로 검증
        // TODO: 비밀번호 암호화 후 저회
        log.debug("account" + dto.getAccount());
        log.debug("password" + dto.getPassword());
        if (authService.createPreActiveTenant(dto)) {
            return BaseResponseDto.<String>builder()
                    .status(200)
                    .message("회원가입 성공")
                    .data(dto.getAccount())
                    .error(null)
                    .timestamp(timeUtil.getCurrentTimeString())
                    .build();
        } else {
            throw new CustomException(GlobalException.BAD_REQUEST);

        }

        // TODO: 회원가입시 보내온 password를 암호화 하여 DB에 저장하고 이때 uuid 생성하여 uuid 필드에 같이 저장
    }


    @PutMapping("/user")
    public BaseResponseDto<TenantDto.UserInfoDto> modifyTenant(
            @RequestBody
            TenantDto.UserInfoDto dto
    ) {
        // 닉네임,이름, 연령대, 전화번호 모두 있다면 권한 설정 변경


        TenantEntity entity = authService.modifyTenant(dto);

        return BaseResponseDto.<TenantDto.UserInfoDto>builder()
                .status(200)
                .message("수정 완료")
                .data(TenantDto.UserInfoDto.entityToDto(entity))
                .error(null)
                .timestamp(timeUtil.getCurrentTimeString())
                .build();
    }


}
