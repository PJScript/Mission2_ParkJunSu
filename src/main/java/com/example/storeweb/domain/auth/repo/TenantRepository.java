package com.example.storeweb.domain.auth.repo;

import com.example.storeweb.domain.auth.dto.TenantDto;
import com.example.storeweb.domain.auth.entity.TenantEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TenantRepository extends JpaRepository<TenantEntity, Long> {
    TenantEntity findTenantEntityByAccount(String account);

    @Cacheable(value = "uuid", key = "#uuid")
    Optional<TenantEntity> findByUuid(String uuid);




}
