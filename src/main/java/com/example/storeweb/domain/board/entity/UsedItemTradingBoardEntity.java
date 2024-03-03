package com.example.storeweb.domain.board.entity;


import com.example.storeweb.domain.auth.entity.TenantEntity;
import com.example.storeweb.common.entity.BaseEntity;
import com.example.storeweb.domain.status.entity.StatusEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

/**
 * 중고거래 상품 목록
 */

@Getter
@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "used_item_trading_board")
public class UsedItemTradingBoardEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private TenantEntity tenant;
    private String title;
    private String desc;

    @Column(name = "min_amount",nullable = false)
    private Integer minAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", nullable = false)
    private StatusEntity status;



}
