package com.example.storeweb.domain.board.controller;

import com.example.storeweb.common.GlobalSystemStatus;
import com.example.storeweb.domain.board.dto.ProductAddResponse;
import com.example.storeweb.domain.board.entity.UsedItemTradingBoardEntity;
import com.example.storeweb.domain.status.dto.ProductAddRequest;
import com.example.storeweb.domain.status.service.BoardService;
import com.example.storeweb.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/board")
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/{name}")
    public void getBoardData(
            @RequestParam
            String name
    ) {
        // TODO: {name} 게시판의 내용을 조회 ( 활성 사용자만 조회할 수 있음 )
    }


    //제목, 설명, 대표 이미지, 최소 가격
    @PostMapping("/product/add")
    public ResponseEntity<ProductAddResponse> addBoardData(
            @RequestPart(value = "thumbnail", required = false)
            MultipartFile file,
            @RequestPart
            ProductAddRequest
                    dto
    ) {


        try {
            UsedItemTradingBoardEntity entity = boardService.productAdd(file, dto);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ProductAddResponse.builder()
                            .postId(entity.getId())
                            .build());

        } catch (IOException e) {
            // 임시
            throw new CustomException(GlobalSystemStatus.BAD_REQUEST);
        }


    }
}
