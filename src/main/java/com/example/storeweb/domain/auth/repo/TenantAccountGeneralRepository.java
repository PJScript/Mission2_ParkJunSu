package com.example.storeweb.domain.auth.repo;

import com.example.storeweb.domain.auth.entity.TenantAccountGeneralEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantAccountGeneralRepository extends JpaRepository<TenantAccountGeneralEntity, Long> {

}
