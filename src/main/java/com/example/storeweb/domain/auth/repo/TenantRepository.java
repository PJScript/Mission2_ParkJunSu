package com.example.storeweb.domain.auth.repo;

import com.example.storeweb.domain.auth.entity.TenantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<TenantEntity, Long> {
    TenantEntity findTenantEntityByAccount(String account);
    TenantEntity findTenantEntityByUuid(String uuid);
}
