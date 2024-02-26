package com.example.storeweb.domain.auth.repo;

import com.example.storeweb.domain.auth.entity.ActivityEntity;
import com.example.storeweb.domain.auth.entity.RoleEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<ActivityEntity,Long> {
    @Cacheable(value = "role", key = "#roleValue")
    List<ActivityEntity> findActivityEntitiesByRoleValue(String roleValue);
    List<ActivityEntity> findByUrlPattern(String urlPattern);
    boolean findActivityEntityByUrlPatternAndRoleId(String urlPattern, Long roleId);
}
