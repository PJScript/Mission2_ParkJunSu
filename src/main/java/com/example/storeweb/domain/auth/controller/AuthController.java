package com.example.storeweb.domain.auth.controller;


import com.example.storeweb.domain.auth.dto.AccountDuplicateResponse;
import com.example.storeweb.domain.auth.dto.LoginRequestDto;
import com.example.storeweb.domain.auth.dto.TenantDto;
import com.example.storeweb.domain.auth.entity.TenantEntity;
import com.example.storeweb.domain.auth.service.AuthService;
import com.example.storeweb.common.dto.BaseResponse;
import com.example.storeweb.exception.CustomException;
import com.example.storeweb.common.GlobalSystemStatus;
import com.example.storeweb.utils.TimeUtil;

import com.example.storeweb.utils.ValidateUtil;
import com.example.storeweb.utils.dto.TokenInfoDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;


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
    private final ValidateUtil validateUtil;

    @GetMapping("/test")
    public String test() {
        log.debug("test");
        return "test";
    }

    /**
     * 로그인 엔드포인트
     */
    @PostMapping("/login")
    public ResponseEntity<TokenInfoDto> login(@RequestBody LoginRequestDto dto, HttpServletRequest req) {
        log.info("account:" + dto.getAccount());
        log.info("password: " + dto.getPassword());
        return ResponseEntity.status(OK).body(
                authService.login(dto, req)
        );
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
    public ResponseEntity<TenantDto.UserInfoDto> getUser(@RequestHeader("Authorization") String jwt, HttpServletRequest req) {
        log.info("HEADER: " + jwt);
        return ResponseEntity.status(OK).body(
                TenantDto.UserInfoDto.entityToDto(authService.getUserInfo(jwt))
        );

    }

    /**
     * 회원가입 엔드포인트
     * <p>아이디, 비밀번호 두가지만으로 가입이 가능하다. <br />
     * 서비스를 이용하려면 닉네임,이름,연령대,이메일,전화번 정보를 추가해야합니다.</p>
     */
    @PostMapping("/join")
    public ResponseEntity<Object> join(@RequestBody TenantDto.JoinRequestDto dto) {

        if (dto.getAccount().isEmpty() || dto.getPassword().isEmpty())
            throw new CustomException(GlobalSystemStatus.BAD_REQUEST);


        if (!validateUtil.accountValidation(dto.getAccount()) || !validateUtil.passwordValidation(dto.getPassword()) || !validateUtil.passwordCheckValidation(dto.getPassword(), dto.getPassword())

        ) throw new CustomException(GlobalSystemStatus.BAD_REQUEST);

        if (authService.duplicateCheck(dto.getAccount())) throw new CustomException(GlobalSystemStatus.DUPLICATE_ACCOUNT);

        authService.createPreActiveTenant(dto);

        // TODO: 비밀번호 암호화 후 저회
        log.debug("account" + dto.getAccount());
        log.debug("password" + dto.getPassword());

        return ResponseEntity.status(CREATED)
                .body(BaseResponse.builder().systemMessage("회원가입 성공").build());

        // TODO: 회원가입시 보내온 password를 암호화 하여 DB에 저장하고 이때 uuid 생성하여 uuid 필드에 같이 저장
    }


    @PutMapping("/user")
    public ResponseEntity<TenantDto.UserInfoDto> modifyTenant(@RequestBody TenantDto.UserInfoDto dto) {
        // 닉네임,이름, 연령대, 전화번호 모두 있다면 권한 설정 변경
        TenantEntity entity = authService.modifyTenant(dto);

        return ResponseEntity.status(OK).body(
                TenantDto.UserInfoDto.entityToDto(entity)
        );
    }


    @GetMapping("/user/account/{account}")
    public ResponseEntity<AccountDuplicateResponse> isDuplicate(@PathVariable String account) {
        boolean isDuplicate = authService.duplicateCheck(account);
        return ResponseEntity.status(CONFLICT).body(
                AccountDuplicateResponse.builder()
                        .isDuplicate(isDuplicate)
                        .build()

        );
    }

}
