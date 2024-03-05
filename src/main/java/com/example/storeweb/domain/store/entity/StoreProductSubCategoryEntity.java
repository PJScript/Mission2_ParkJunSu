package com.example.storeweb.domain.store.entity;

import com.example.storeweb.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "store_product_sub_category")
public class StoreProductSubCategoryEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_product_main_category_id",referencedColumnName = "id")
    private StoreProductMainCategoryEntity storeProductMainCategory;

    @Column(name = "value", nullable = false)
    private String value;

    @Column(name = "view_value", nullable = false)
    private String viewValue;

}
