package com.example.storeweb.domain.auth.dto;

import com.example.storeweb.common.dto.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class AccountDuplicateResponse {
    private boolean isDuplicate;
}
