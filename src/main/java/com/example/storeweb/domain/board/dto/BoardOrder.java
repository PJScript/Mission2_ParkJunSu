package com.example.storeweb.domain.board.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardOrder {
    private Long id;
    private String purchaseRequestUserUuid;
    private String purchaseRequestUserName;
    private String purchaseRequestUserNickname;
    private String productTitle;
    private String productDesc;
    private Long productId;
    private String status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
