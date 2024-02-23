package com.example.storeweb.auth.entity;

import com.example.storeweb.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
@Table(name = "tenant")
public class TenantEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "tenant")
    private List<TenantAccountGeneralEntity> tenantAccountGeneralEntities;

    @OneToMany(mappedBy = "tenant")
    private List<TenantAccountBuiesnessEntity> tenantAccountBuiesnessEntities;


    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private RoleEntity role;

    @ManyToOne
    @JoinColumn(name = "tenant_type_id",referencedColumnName = "id")
    private TenantTypeEntity tenantType;
    private String uuid;
    private String account;
    private String password;
    private String nickname;
    private String name;
    private String age;
    private String email;
    private String phone1;
    private String phone2;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "is_integrate",nullable = false)
    private boolean isIntegrate;


    /** 일반 유저인지, 소셜 연동 유저인지, 소셜이라면 무슨 소셜 연동인지*/
    @ManyToOne
    @JoinColumn(name = "login_type_id",referencedColumnName = "id")
    private TenantLoginTypeEntity loginType;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "address_detail")
    private String addressDetail;

    /** 광역자치단체 단위 (최상위)  */
    @Column(name = "privince_level_division", nullable = false)
    private String privinceLevelDivision;

    /**시,군,구*/
    @Column(name = "municipal_level_division",nullable = false)
    private String municipalLevelDivision;

    /**음,면,동*/
    @Column(name = "sub_municipla_level_division", nullable = false)
    private String subMunicipalLevelDivision;
}
