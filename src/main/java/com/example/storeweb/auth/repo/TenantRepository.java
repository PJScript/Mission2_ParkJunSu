package com.example.storeweb.auth.repo;

import com.example.storeweb.auth.dto.TenantDto;
import com.example.storeweb.auth.dto.UserInfoDto;
import com.example.storeweb.auth.entity.TenantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<TenantEntity, Long> {
    TenantEntity findTenantEntityByAccount(String account);
    TenantEntity findTenantEntityByUuid(String uuid);
}
