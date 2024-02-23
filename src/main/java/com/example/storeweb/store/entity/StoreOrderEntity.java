package com.example.storeweb.store.entity;

import com.example.storeweb.common.entity.BaseEntity;
import com.example.storeweb.status.entity.StatusEntity;
import jakarta.persistence.*;
import lombok.Getter;

/**
 * 스토어의 주문서 목록 입니다. <br />
 * 하나의 주문서에는 여러개의 주문이 포함될 수 있습니다.
 * */

@Getter
@Entity
public class StoreOrderEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_id",referencedColumnName = "id")
    private StoreEntity store;

    @ManyToOne
    @JoinColumn(name = "store_order_detail_id",referencedColumnName = "id")
    private StoreOrderDetailEntity storeOrderDetail;

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private StatusEntity status;
}
