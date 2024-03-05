package com.example.storeweb.domain.status.repo;

import com.example.storeweb.domain.status.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatusRepository extends JpaRepository<StatusEntity,Long> {

    Optional<StatusEntity> findStatusEntityByValueAndType(String value, String type);
}
