package com.example.storeweb.domain.auth.dto;

import com.example.storeweb.domain.auth.entity.TenantEntity;
import lombok.*;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

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

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class UserInfoDto {
        private String uuid;
        private String account;
        private String address;
        private String addressDetail;
        private String age;
        private String email;
        private String name;
        private String nickname;
        private String phone1;
        private String phone2;
        private String profileImageUrl;
        private String privinceLevelDivision;
        private String municipalLevelDivision;
        private String subMunicipalLevelDivision;

        public static UserInfoDto entityToDto(TenantEntity entity) {
            return UserInfoDto.builder()
                    .uuid(entity.getUuid())
                    .account(Optional.ofNullable(entity.getAccount()).orElse(""))
                    .address(Optional.ofNullable(entity.getAddress()).orElse(""))
                    .addressDetail(Optional.ofNullable(entity.getAddressDetail()).orElse(""))
                    .age(Optional.ofNullable(entity.getAge()).orElse(""))
                    .email(Optional.ofNullable(entity.getEmail()).orElse(""))
                    .name(Optional.ofNullable(entity.getName()).orElse(""))
                    .nickname(Optional.ofNullable(entity.getNickname()).orElse(""))
                    .phone1(Optional.ofNullable(entity.getPhone1()).orElse(""))
                    .phone2(Optional.ofNullable(entity.getPhone2()).orElse(""))
                    .profileImageUrl(Optional.ofNullable(entity.getProfileImageUrl()).orElse(""))
                    .privinceLevelDivision(Optional.ofNullable(entity.getPrivinceLevelDivision()).orElse(""))
                    .municipalLevelDivision(Optional.ofNullable(entity.getMunicipalLevelDivision()).orElse(""))
                    .subMunicipalLevelDivision(Optional.ofNullable(entity.getSubMunicipalLevelDivision()).orElse(""))
                    .build();
        }


        public static TenantEntity dtoToEntity(UserInfoDto dto, TenantEntity tenant){
            return TenantEntity.builder()
                    .id(tenant.getId())
                    .uuid(Optional.ofNullable(dto.getUuid()).orElse(""))
                    .password(tenant.getPassword())
                    .account(tenant.getAccount())
                    .address(Optional.ofNullable(dto.getAddress()).orElse(""))
                    .addressDetail(Optional.ofNullable(dto.getAddressDetail()).orElse(""))
                    .age(Optional.ofNullable(dto.getAge()).orElse(""))
                    .email(Optional.ofNullable(dto.getEmail()).orElse(""))
                    .name(Optional.ofNullable(dto.getName()).orElse(""))
                    .nickname(Optional.ofNullable(dto.getNickname()).orElse(""))
                    .phone1(Optional.ofNullable(dto.getPhone1()).orElse(""))
                    .phone2(Optional.ofNullable(dto.getPhone2()).orElse(""))
                    .profileImageUrl(Optional.ofNullable(dto.getProfileImageUrl()).orElse(""))
                    .privinceLevelDivision(Optional.ofNullable(dto.getPrivinceLevelDivision()).orElse(""))
                    .municipalLevelDivision(Optional.ofNullable(dto.getMunicipalLevelDivision()).orElse(""))
                    .subMunicipalLevelDivision(Optional.ofNullable(dto.getSubMunicipalLevelDivision()).orElse(""))
                    .tenantAccountBuiesnessEntities(tenant.getTenantAccountBuiesnessEntities())
                    .tenantAccountGeneralEntities(tenant.getTenantAccountGeneralEntities())
                    .role(tenant.getRole())
                    .loginType(tenant.getLoginType())
                    .isIntegrate(tenant.isIntegrate())

                    .build();
        }


    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class IsDuplcateAccountDto{
        boolean isDuplicate;
    }
}
