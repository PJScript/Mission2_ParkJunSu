package com.example.storeweb.auth.controller;

import com.example.storeweb.auth.dto.LoginRequestDto;
import com.example.storeweb.auth.dto.LoginResponseDto;
import com.example.storeweb.auth.dto.UserInfoDto;
import com.example.storeweb.auth.entity.TenantEntity;
import com.example.storeweb.auth.service.AuthService;
import com.example.storeweb.common.dto.BaseResponseDto;
import com.example.storeweb.utils.dto.TokenInfoDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * TenantController
 * <p>유저 정보에 관한 컨트롤러</p>
 * @version v1
 * */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/auth")
public class TenantController {
    private final AuthService authService;

    @GetMapping("/test")
    public String test(){
        log.debug("test");
        return "test";
    }

    /**
     * 로그인 엔드포인트
     * */
    @PostMapping("/login")
    public BaseResponseDto<TokenInfoDto> login(
            @RequestBody
            LoginRequestDto dto
    ) {
        log.info("account:" + dto.getAccount());
        log.info("password: " + dto.getPassword());


        return authService.login(dto);
    }

    /**
     * 유저조회 엔드포인트
     *
     * <p>
     * JWT로 전달받은 <b>uuid</B> 를 통해 타겟 유저의 정보를 가져옵니다.
     *
     * </p>
     *
     * */
    @GetMapping("/user")
    public BaseResponseDto<UserInfoDto> getUser(
            @RequestHeader("Authorization")
            String jwt

    ) {
        log.info("HEADER: "+ jwt);

        // TODO: JWT로 받아온 값을 해석하여 유저 고유값인 uuid와 대조한 후 정보 전달

        return BaseResponseDto.of(200,"조회성공",authService.getUserInfo(jwt));
    }

    /**
     * 회원가입 엔드포인트
     * <p>아이디, 비밀번호 두가지만으로 가입이 가능하다. <br />
     * 서비스를 이용하려면 닉네임,이름,연령대,이메일,전화번 정보를 추가해야합니다.</p>
     *
     * */
    @PostMapping("/join")
    public void join(
            @RequestBody
            String account,
            String password
    ){
        log.debug("account" + account);
        log.debug("password" + password);
      // TODO: 회원가입시 보내온 password를 암호화 하여 DB에 저장하고 이때 uuid 생성하여 uuid 필드에 같이 저장
    }



}
