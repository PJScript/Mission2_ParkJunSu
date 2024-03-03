package com.example.storeweb.domain.board.dto;


import lombok.Getter;

@Getter
public class ProductAddRequest {
    private String title;
    private String desc;
    private Integer minAmount;
}
