package com.example.storeweb.domain.board.controller;

import com.example.storeweb.domain.board.dto.BoardOrder;
import com.example.storeweb.domain.board.entity.UsedItemTradeOrderEntity;
import com.example.storeweb.domain.board.service.BoardOrderService;
import lombok.RequiredArgsConstructor;
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


    @GetMapping("/product/{id}/purchase-request")
    public ResponseEntity<List<BoardOrder>> getPurchaseRequest(
            @PathVariable
            Long id,
            Pageable pageable
    ){
        Page<UsedItemTradeOrderEntity> entities = boardOrderService.getPurchaseRequest(pageable, id);

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
}
