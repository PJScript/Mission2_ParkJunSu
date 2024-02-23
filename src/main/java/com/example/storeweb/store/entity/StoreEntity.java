package com.example.storeweb.store.entity;

import com.example.storeweb.auth.entity.TenantEntity;
import com.example.storeweb.common.entity.BaseEntity;
import com.example.storeweb.status.entity.StatusEntity;
import jakarta.persistence.*;
import jdk.jfr.Category;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "store")
public class StoreEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tenant_id",referencedColumnName = "id")
    private TenantEntity tenant;

    @ManyToOne
    @JoinColumn(name = "category_id",referencedColumnName = "id")
    private StoreCategoryEntity storeCategory;

    @ManyToOne
    @JoinColumn(name = "status_id",referencedColumnName = "id")
    private StatusEntity status;
    private String name;
    private String info;

    @Column(name = "closure_request_date")
    private LocalDateTime closureRequestDate;

    @Column(name = "closure_success_date")
    private LocalDateTime closureSuccessDate;

    @Column(name = "open_request_date")
    private LocalDateTime openRequestDate;

    @Column(name = "open_preparing_date")
    private LocalDateTime openPreparingDate;

    @Column(name = "open_success_date")
    private LocalDateTime openSuccessDate;
}
