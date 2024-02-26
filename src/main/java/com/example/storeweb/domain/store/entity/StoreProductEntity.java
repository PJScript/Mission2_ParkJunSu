package com.example.storeweb.domain.store.entity;

import com.example.storeweb.common.entity.BaseEntity;
import com.example.storeweb.domain.status.entity.StatusEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "store_product")
public class StoreProductEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_id",referencedColumnName = "id")
    private StoreEntity store;

    @ManyToOne
    @JoinColumn(name = "main_category_id",referencedColumnName = "id")
    private StoreProductMainCategoryEntity storeProductMainCategory;

    @ManyToOne
    @JoinColumn(name = "sub_category_id",referencedColumnName = "id")
    private StoreProductSubCategoryEntity storeProductSubCategoryEntity;

    private String value;

    @Column(name = "view_value", nullable = false)
    private String viewValue;

    @Column(name = "product_title", nullable = false)
    private String productTitle;
    private Integer amount;

    @Column(name = "discount_rate",nullable = false)
    private double discountRate;

    @Column(name = "final_amount", nullable = false)
    private double finalAmount;

    @ManyToOne
    @JoinColumn(name = "status_id",referencedColumnName = "id")
    private StatusEntity status;
}
