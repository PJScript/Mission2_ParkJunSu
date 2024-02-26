package com.example.storeweb.domain.auth.repo;

import com.example.storeweb.domain.auth.entity.RoleEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<RoleEntity,Long> {
    @Cacheable(value = "roles",key = "'roles'")
    List<RoleEntity> findAll();
}
