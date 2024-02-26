package com.example.storeweb.domain.auth.entity;


import com.example.storeweb.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Optional;

@Getter
@Entity
@ToString
@Table(name = "role")
public class RoleEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Tenant_General_Active : 활성화된 일반 사용자 <br />
     * Tenant_Buiesness_Active : 활성화된 비즈니스 사용자 <br />
     * Tenant_Pre_Active : 비활성화 상태인 사용자 <br />
     * Service_Master_Admin : 전체 서비스 최고 관리자<br />
     * */

    @Column(nullable = false)
    private String value;

    @Column(name = "view_value", nullable = false)
    private String viewValue;

    @OneToMany(mappedBy = "role")
    private List<ActivityEntity> activities;



}
