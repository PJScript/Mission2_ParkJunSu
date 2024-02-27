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
@ToString
@Builder
public class BaseResponseDto<D> {
    private static final ObjectMapper objectMapper = new ObjectMapper();


    private final int status;
    private final String message;
    private final D data;
//    private final String path;
    private final String error;
    private final String timestamp;

    public String convertToJson() throws JsonProcessingException {
        return objectMapper.writeValueAsString(this);
    }
}

