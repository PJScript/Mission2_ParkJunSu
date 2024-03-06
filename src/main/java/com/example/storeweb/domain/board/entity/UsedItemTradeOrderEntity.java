package com.example.storeweb.domain.board.entity;

import com.example.storeweb.domain.auth.entity.TenantEntity;
import com.example.storeweb.common.entity.BaseEntity;
import com.example.storeweb.domain.status.entity.StatusEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "used_item_trade_order")
public class UsedItemTradeOrderEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "buyer_tenant_id", nullable = false)
    private TenantEntity tenant;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private UsedItemTradingBoardEntity board;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private StatusEntity status;

    @Column(name = "is_payment_confirm", nullable = false)
    private boolean isPaymentConfirm;
}
