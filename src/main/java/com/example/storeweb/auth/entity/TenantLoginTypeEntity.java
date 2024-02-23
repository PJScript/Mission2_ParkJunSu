package com.example.storeweb.auth.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "tenant_login_type")
public class TenantLoginTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value",nullable = false)
    private String value;

    @Column(name = "view_value", nullable = false)
    private String viewValue;

}
