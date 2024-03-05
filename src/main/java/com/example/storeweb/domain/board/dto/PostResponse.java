package com.example.storeweb.domain.board.dto;

import com.example.storeweb.domain.board.entity.UsedItemTradingBoardEntity;
import lombok.Builder;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class PostResponse {
    private Long id;
    private String title;
    private String thumbnail;
    private String desc;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PostResponse convertToDto(UsedItemTradingBoardEntity entity){

        return PostResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .desc(entity.getDesc())
                .thumbnail("")
                .createdAt(entity.getCreateDate())
                .updatedAt(entity.getUpdateDate())
                .build();

    };

}
