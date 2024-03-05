package com.example.storeweb.domain.board.service;


import com.example.storeweb.common.GlobalSystemStatus;
import com.example.storeweb.domain.auth.entity.TenantEntity;
import com.example.storeweb.domain.auth.repo.TenantRepository;
import com.example.storeweb.domain.board.entity.UsedItemTradeOrderEntity;
import com.example.storeweb.domain.board.entity.UsedItemTradingBoardEntity;
import com.example.storeweb.domain.board.repo.BoardOrderRepository;
import com.example.storeweb.domain.board.repo.BoardRepository;
import com.example.storeweb.domain.status.entity.StatusEntity;
import com.example.storeweb.domain.status.repo.StatusRepository;
import com.example.storeweb.exception.CustomException;
import com.example.storeweb.utils.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardOrderService {
    private final StatusRepository statusRepository;
    private final TenantRepository tenantRepository;
    private final BoardRepository boardRepository;
    private final BoardOrderRepository boardOrderRepository;

    /**
     * 구매 요청을 처리하는 메서드
     */
    @Transactional
    public void purchaseRequest(Long id) {

        Optional<UsedItemTradingBoardEntity> board = boardRepository.findById(id);
        if (board.isEmpty()) {
            throw new CustomException(GlobalSystemStatus.ACCESS_DENIED);
        }

        Optional<TenantEntity> tenant = tenantRepository.findTenantEntityByUuid(SecurityUtil.getCurrentUserDetails().getUsername());
        if (tenant.isEmpty()) {
            throw new CustomException(GlobalSystemStatus.ACCESS_DENIED);
        }

        String PURCHASE_REQUEST = "PURCHASE_REQUST";
        String TYPE = "BOARD_ITEM";
        StatusEntity status = statusRepository.findStatusEntityByValueAndType(PURCHASE_REQUEST, TYPE);


        UsedItemTradeOrderEntity entity = UsedItemTradeOrderEntity.builder()
                .tenant(tenant.get())
                .board(board.get())
                .status(status)
                .isPaymentConfirm(false)
                .build();

        boardOrderRepository.save(entity);
    }

    /**
     * 구매 요청 승인을 처리하는 메서드
     */
    public void purchaseConfirm() {

    }

    /**
     * 구매 요청 대기를 처리하는 메서드
     */
    public void purchaseWait() {

    }
}
