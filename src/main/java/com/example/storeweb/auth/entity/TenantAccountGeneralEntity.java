package com.example.storeweb.auth.entity;

import com.example.storeweb.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

@Getter
@Entity
@Table(name = "tenant_account_general")
public class TenantAccountGeneralEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", referencedColumnName = "id")
    private TenantEntity tenant;

//    @ManyToOne()
//    @JoinColumn(name = "role_id", referencedColumnName = "id")
//    private TenantAccountGeneralEntity tenantAccountGeneral;


}
