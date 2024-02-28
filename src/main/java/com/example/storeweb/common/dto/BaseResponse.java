package com.example.storeweb.common.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;


/**
 * <p>
 *    공통 응답 DTO
 * </p>
 * */
@Getter
@Setter
@Builder
public class BaseResponse {
    private String systemCode;
    private String systemMessage;
}

