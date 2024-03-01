package com.example.storeweb.domain.auth.repo;

import com.example.storeweb.domain.auth.dto.TenantDto;
import com.example.storeweb.domain.auth.entity.TenantEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TenantRepository extends JpaRepository<TenantEntity, Long> {
    Optional<TenantEntity> findTenantEntityByAccount(String account);

    @Cacheable(value = "tenant-by-uuid", key = "#uuid")
    Optional<TenantEntity> findTenantEntityByUuid(String uuid);

    Optional<TenantEntity> findByAccount(String account);





}
