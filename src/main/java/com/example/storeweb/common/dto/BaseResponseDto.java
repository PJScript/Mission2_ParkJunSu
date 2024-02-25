package com.example.storeweb.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


/**
 * <p>
 *    공통 응답 DTO
 * </p>
 * */
@Getter
@Setter
@RequiredArgsConstructor(staticName = "of")
public class BaseResponseDto<D> {
    private final Integer status;
    private final String message;
    private final D data;
}
