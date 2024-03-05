package com.example.storeweb.domain.board.controller;

import com.example.storeweb.domain.board.dto.BoardOrder;
import com.example.storeweb.domain.board.entity.UsedItemTradeOrderEntity;
import com.example.storeweb.domain.board.entity.UsedItemTradingBoardEntity;
import com.example.storeweb.domain.board.service.BoardOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *     사용자의 구매제안, 거절 등 처리
 * </p>
 * */

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/board")
public class BoardOrderController {
    private final BoardOrderService boardOrderService;


    @PostMapping("/product/{id}/purchase-request")
    public ResponseEntity<Object> purchaseRequest(
            @PathVariable
            Long id
    ){
        boardOrderService.purchaseRequest(id);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }


    @GetMapping("/product/{id}/all-purchase-request")
    public ResponseEntity<List<BoardOrder>> getAllPurchaseRequest(
            @PathVariable
            Long id,
            Pageable pageable
    ){
        Page<UsedItemTradeOrderEntity> entities = boardOrderService.getAllPurchaseRequest(pageable, id);

        List<BoardOrder> boardOrders = entities.stream().map(
                entity -> BoardOrder.builder()
                        .id(entity.getId())
                        .purchaseRequestUserUuid(entity.getTenant().getUuid())
                        .purchaseRequestUserName(entity.getTenant().getName())
                        .purchaseRequestUserNickname(entity.getTenant().getNickname())
                        .productId(entity.getBoard().getId())
                        .productTitle(entity.getBoard().getTitle())
                        .productDesc(entity.getBoard().getDesc())
                        .status(entity.getStatus().getViewValue())
                        .updatedAt(entity.getUpdateDate())
                        .createdAt(entity.getCreateDate())
                        .build()
        ).toList();

        return ResponseEntity.status(HttpStatus.OK).body(
                boardOrders
        );
    }

    @GetMapping("/product/{id}/my-purchase-request")
    public ResponseEntity<List<BoardOrder>> getMyPurchaseRequest(
            @PathVariable
            Long id,
            Pageable pageable
    ){
        Page<UsedItemTradeOrderEntity> entities = boardOrderService.getMyPurchaseRequest(pageable, id);

        List<BoardOrder> boardOrders = entities.stream().map(
                entity -> BoardOrder.builder()
                        .id(entity.getId())
                        .purchaseRequestUserUuid(entity.getTenant().getUuid())
                        .purchaseRequestUserName(entity.getTenant().getName())
                        .purchaseRequestUserNickname(entity.getTenant().getNickname())
                        .productId(entity.getBoard().getId())
                        .productTitle(entity.getBoard().getTitle())
                        .productDesc(entity.getBoard().getDesc())
                        .status(entity.getStatus().getViewValue())
                        .updatedAt(entity.getUpdateDate())
                        .createdAt(entity.getCreateDate())
                        .build()
        ).toList();

        return ResponseEntity.status(HttpStatus.OK).body(
                boardOrders
        );
    }


    @PostMapping("/purchase-request/{orderId}/accept")
    public ResponseEntity<BoardOrder> acceptPurchaseRequest(
            @PathVariable("orderId")
            Long orderId
    ){
        UsedItemTradeOrderEntity entity = boardOrderService.acceptPurchaseRequest(orderId);
        BoardOrder boardOrder = BoardOrder.builder()
                .id(entity.getId())
                .purchaseRequestUserUuid(entity.getTenant().getUuid())
                .purchaseRequestUserName(entity.getTenant().getName())
                .purchaseRequestUserNickname(entity.getTenant().getNickname())
                .productId(entity.getBoard().getId())
                .productTitle(entity.getBoard().getTitle())
                .productDesc(entity.getBoard().getDesc())
                .status(entity.getStatus().getViewValue())
                .updatedAt(entity.getUpdateDate())
                .createdAt(entity.getCreateDate())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(boardOrder);
    }

    @PostMapping("/purchase-request/{orderId}/refuse")
    public ResponseEntity<Object> refusePurchaseRequest(
            @PathVariable("orderId")
            Long orderId
    ){
        UsedItemTradeOrderEntity entity = boardOrderService.refusePurchaseRequest(orderId);
        BoardOrder boardOrder = BoardOrder.builder()
                .id(entity.getId())
                .purchaseRequestUserUuid(entity.getTenant().getUuid())
                .purchaseRequestUserName(entity.getTenant().getName())
                .purchaseRequestUserNickname(entity.getTenant().getNickname())
                .productId(entity.getBoard().getId())
                .productTitle(entity.getBoard().getTitle())
                .productDesc(entity.getBoard().getDesc())
                .status(entity.getStatus().getViewValue())
                .updatedAt(entity.getUpdateDate())
                .createdAt(entity.getCreateDate())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(boardOrder);
    }
}
