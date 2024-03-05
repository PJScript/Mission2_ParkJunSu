package com.example.storeweb.domain.board.controller;

import com.example.storeweb.domain.board.service.BoardOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
