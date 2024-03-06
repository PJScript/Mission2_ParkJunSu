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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        Optional<StatusEntity> status = statusRepository.findStatusEntityByValueAndType(PURCHASE_REQUEST, TYPE);

        if (status.isEmpty()) {
            throw new CustomException(GlobalSystemStatus.BAD_REQUEST);
        }

        UsedItemTradeOrderEntity entity = UsedItemTradeOrderEntity.builder()
                .tenant(tenant.get())
                .board(board.get())
                .status(status.get())
                .isPaymentConfirm(false)
                .build();

        boardOrderRepository.save(entity);
    }

    /**
     * 내가 넣은 요청만 조회
     */
    @Cacheable(value = "orders-mine", key = "#boardItemId + '_' + T(com.example.storeweb.utils.SecurityUtil).getCurrentUserDetails().getUsername()")
    public Page<UsedItemTradeOrderEntity> getMyPurchaseRequest(Pageable pageable, Long boardItemId) {
        Optional<UsedItemTradingBoardEntity> entity = boardRepository.findById(boardItemId);
        if (entity.isEmpty()) {
            throw new CustomException(GlobalSystemStatus.BAD_REQUEST);
        }

        String uuid = SecurityUtil.getCurrentUserDetails().getUsername();
        return boardOrderRepository.findAllByBoardIdAndTenantUuid(pageable, boardItemId, uuid);
    }

    /**
     * 모든 기록을 조회
     */
    @Cacheable(value = "orders-all", key = "#boardItemId + '_' + T(com.example.storeweb.utils.SecurityUtil).getCurrentUserDetails().getUsername()")
    public Page<UsedItemTradeOrderEntity> getAllPurchaseRequest(Pageable pageable, Long boardItemId) {
        Optional<UsedItemTradingBoardEntity> boardEntity = boardRepository.findById(boardItemId);

        if(boardEntity.isEmpty()){
            throw new CustomException(GlobalSystemStatus.NOT_FOUND);
        }

        // 게시물 작성자가 아니라면 모든 기록을 조회할 수 없음
        if(!boardEntity.get().getTenant().getUuid().equals(SecurityUtil.getCurrentUserDetails().getUsername())){
            throw new CustomException(GlobalSystemStatus.ACCESS_DENIED);
        }


        return boardOrderRepository.findAllByBoardId(pageable, boardItemId);
    }

    /**
     * 구매 제안 수락
     */
    @Transactional
    @CacheEvict(value = {"orders-mine", "orders-all"}, allEntries = true)
    public UsedItemTradeOrderEntity acceptPurchaseRequest(Long orderId) {
        String VALUE = "PURCHASE_REQUEST_ACCEPT";
        String TYPE = "BOARD_ITEM_ORDER";

        Optional<UsedItemTradeOrderEntity> order = boardOrderRepository.findById(orderId);
        if (order.isEmpty()) {
            log.warn("구매 요청 기록을 찾을 수 없습니다 - orderId : " + orderId);
            throw new CustomException(GlobalSystemStatus.NOT_FOUND);
        }

        String uuid = SecurityUtil.getCurrentUserDetails().getUsername();
        String writerUuid = order.get().getBoard().getTenant().getUuid();

        if (!writerUuid.equals(uuid)) {
            log.warn("권한 없는 사용자 - UUID : " + uuid);
            throw new CustomException(GlobalSystemStatus.ACCESS_DENIED);
        }

        Optional<StatusEntity> status = statusRepository.findStatusEntityByValueAndType(VALUE, TYPE);

        if (status.isEmpty()) {
            log.warn("상태값을 찾을 수 없습니다. 상태 테이블을 확인해주세요");
            throw new CustomException(GlobalSystemStatus.NOT_FOUND);
        }

        if (order.get().getBoard().isDelete()) {
            log.warn("이미 삭제 처리된 게시물 입니다.");
            throw new CustomException(GlobalSystemStatus.NOT_FOUND);
        }

        UsedItemTradeOrderEntity entity = UsedItemTradeOrderEntity.builder()
                .id(order.get().getId())
                .status(status.get())
                .board(order.get().getBoard())
                .tenant(order.get().getTenant())
                .isPaymentConfirm(order.get().isPaymentConfirm())
                .createDate(order.get().getCreateDate())
                .updateDate(order.get().getUpdateDate())
                .build();

        refuseOtherPurchaseRequest(order.get().getId());
        return boardOrderRepository.save(entity);
    }

    /**
     * 구매 제안 거절
     */
    @CacheEvict(value = {"orders-mine", "orders-all"}, allEntries = true)
    public UsedItemTradeOrderEntity refusePurchaseRequest(Long orderId) {
        String VALUE = "PURCHASE_REQUEST_REFUSE";
        String TYPE = "BOARD_ITEM_ORDER";

        Optional<UsedItemTradeOrderEntity> order = boardOrderRepository.findById(orderId);
        if (order.isEmpty()) {
            log.warn("구매 요청 기록을 찾을 수 없습니다 - orderId : " + orderId);
            throw new CustomException(GlobalSystemStatus.NOT_FOUND);
        }

        String uuid = SecurityUtil.getCurrentUserDetails().getUsername();
        String writerUuid = order.get().getBoard().getTenant().getUuid();

        if (!writerUuid.equals(uuid)) {
            log.warn("권한 없는 사용자 - UUID : " + uuid);
            throw new CustomException(GlobalSystemStatus.ACCESS_DENIED);
        }

        Optional<StatusEntity> status = statusRepository.findStatusEntityByValueAndType(VALUE, TYPE);

        if (status.isEmpty()) {
            log.warn("상태값을 찾을 수 없습니다. 상태 테이블을 확인해주세요");
            throw new CustomException(GlobalSystemStatus.NOT_FOUND);
        }

        if (order.get().getBoard().isDelete()) {
            log.warn("이미 삭제 처리된 게시물 입니다.");
            throw new CustomException(GlobalSystemStatus.NOT_FOUND);
        }

        UsedItemTradeOrderEntity entity = UsedItemTradeOrderEntity.builder()
                .id(order.get().getId())
                .status(status.get())
                .board(order.get().getBoard())
                .tenant(order.get().getTenant())
                .isPaymentConfirm(order.get().isPaymentConfirm())
                .createDate(order.get().getCreateDate())
                .updateDate(order.get().getUpdateDate())
                .build();
        return boardOrderRepository.save(entity);
    }

    public void refuseOtherPurchaseRequest(Long acceptedOrderId) {
        boardOrderRepository.refuseAllOrdersExcept(acceptedOrderId);
    }


}
