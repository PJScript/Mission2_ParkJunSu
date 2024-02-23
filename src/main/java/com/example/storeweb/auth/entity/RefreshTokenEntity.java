package com.example.storeweb.auth.entity;


import com.example.storeweb.common.entity.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_token")
public class RefreshTokenEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tenant_id", referencedColumnName = "id")
    private TenantEntity tenant;

    @Column(name = "expire_date", nullable = false)
    private LocalDateTime expireDate;



}
