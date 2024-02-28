package com.example.storeweb.utils;

import org.springframework.stereotype.Component;

@Component
public class ValidateUtil {
    private static final String ACCOUNT_PATTERN = "^[a-zA-Z0-9]{5,20}$";
    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%])(?=\\S+$).{8,}$";
    private static final String BUSINESS_NUMBER_PATTERN = "";
    /**
     * <p>계정정보 유효성 검사</p>
     * <p>5글자 이상 20글자 이하이며 특수문자를 허용하지 않음</p>
     * @param account 검사할 문자열
     * @return 검증 결과 true / false
     * */
    public boolean accountValidation(String account) {
        return account.matches(ACCOUNT_PATTERN);
    }

    /**
     * <p>비밀번호 유효성 검사</p>
     * <p> 8글자 이상, 영어 대소문자, 숫자 조합 <br />
     * 특수문자 조합시 !@#$% 이외의 특수문자는 허용하지 않음
     *
     * @param password 검증할 비밀번호
     * @return 검증 결과 true / false
     * </p>
     * */
    public boolean passwordValidation(String password){
        return password.matches(PASSWORD_PATTERN);
    }

    /**
     * <p>비밀번호 재입력 유효성 검사</p>
     * <p>비밀번호 변경, 혹은 회원가입시 비밀번호 재입력 필드의 값을 검증</p>
     * @param password 비밀번호 필드에 입력한 값
     * @param passwordCheck 비밀번호 확인 필드에 입력한 값
     * @return 검증 결과 true / false
     * */
    public boolean passwordCheckValidation(String password, String passwordCheck){
        return password.equals(passwordCheck);
    }


    /**
     * <p>사업자 등록번호 유효성 검사</p>
     * <p>사업자 등록 번호 형식이 올바른지 검증 <br />
     * 현재 요구사항엔 없음 우선 항상  true 리턴
     * @param buiessnessNumber 검사할 사업자 등록번호
     * @return 검증 결과 true / false
     * </p>
     * */
    public boolean buiessnessNumberValidation(String buiessnessNumber){

        return true;
    }

}
