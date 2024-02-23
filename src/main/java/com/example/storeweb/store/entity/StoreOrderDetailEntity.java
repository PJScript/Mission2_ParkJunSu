package com.example.storeweb.store.entity;

import com.example.storeweb.common.entity.BaseEntity;
import com.example.storeweb.status.entity.StatusEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Entity
@Table(name = "store_order_detail")
public class StoreOrderDetailEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // product_id

    /** 주문당시 상품 가격*/
    @Column(name = "order_detail_amount", nullable = false)
    private BigDecimal orderDetailAmount;

    /** 할인 적용 적 상품 총합 <br />
     * 계산식 : quantity * order_detail_amount
     * */
    @Column(name = "order_detail_total_price", nullable = false)
    private BigDecimal orderDetailTotalPrice;

    /** 할인 등 혜택이 모두 적용된 최종 청구가격  <br />
     * 계산식 : quantity * order_detail_amount * discount_rate  <br />
     * 이후 기타 쿠폰 및 혜택을 해당 쿠폰 및 혜택에서 정의한 계산식에 맞게 계산
     * */
    @Column(name = "order_detail_final_amount", nullable = false)
    private BigDecimal orderDetailFinalAmount;

    /**
     * 주문당시 상품의 상태값 ex) 품절 등
     *
     * */
    @ManyToOne
    @JoinColumn(name = "order_detail_product_status_id", referencedColumnName = "id")
    private StatusEntity orderDetailProductStatus;
    private Integer quantity;

    /** 소숫점아래 2자리 까지만 저장*/
    @Column(name = "discount_rate", nullable = false)
    private double discountRate;

    /** 현재 오더의 상태<br/>
     * 결제완료, 결제거부 등
     * */
    @ManyToOne
    @JoinColumn(name = "payment_status_id", referencedColumnName = "id")
    private StatusEntity paymentStatusId;




}
