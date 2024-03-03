package com.example.storeweb.domain.status.repo;

import com.example.storeweb.domain.status.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<StatusEntity,Long> {

    StatusEntity findStatusEntityByValueAndType(String value, String type);
}
