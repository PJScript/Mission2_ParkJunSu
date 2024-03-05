package com.example.storeweb.domain.status.entity;

import com.example.storeweb.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "status")
public class StatusEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String value;

    @Column(name = "view_value")
    private String viewValue;

    // PAYMENT, STORE,STORE_ITEM_IMAGE, BOARD_ITEM,BOARD_ITEM_IMAGE, STORE_PRODUCT
    private String type;

}
