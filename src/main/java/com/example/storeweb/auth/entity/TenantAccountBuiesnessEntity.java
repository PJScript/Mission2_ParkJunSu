package com.example.storeweb.auth.entity;


import com.example.storeweb.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "tenant_account_buiesness")
public class TenantAccountBuiesnessEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tenant_id", referencedColumnName = "id")
    private TenantEntity tenant;


    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private RoleEntity role;


    @Column(name = "buisness_number", nullable = false)
    private Integer buisnessNumber;

    @Column(name = "buisness_address", nullable = false)
    private String buisnessAddress;

    @Column(name = "buisness_address_detail")
    private String buisnessAddressDetail;

    @Column(name = "buisness_province_level_division", nullable = false)
    private String buisnessProvince;

    @Column(name = "buisness_municipal_level_division", nullable = false)
    private String buisnessDistrict;

    @Column(name = "buisness_submunicipal_level_division", nullable = false)
    private String buisnessSubDistrict;

}
