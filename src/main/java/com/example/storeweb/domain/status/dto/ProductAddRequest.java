package com.example.storeweb.domain.status.dto;


import lombok.Getter;

@Getter
public class ProductAddRequest {
    private String title;
    private String desc;
    private Integer minAmount;
}
