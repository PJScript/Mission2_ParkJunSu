package com.example.storeweb.domain.board.controller;

import com.example.storeweb.common.GlobalSystemStatus;
import com.example.storeweb.domain.status.dto.ProductAddRequest;
import com.example.storeweb.domain.status.service.BoardService;
import com.example.storeweb.exception.CustomException;
import lombok.RequiredArgsConstructor;
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
    public void addBoardData(
            @RequestPart(value = "thumbnail", required = false)
            MultipartFile file,
            @RequestPart
            ProductAddRequest
                    dto
    )  {
        try {
            boardService.productAdd(file, dto);
        } catch (IOException e) {
            // 임시
            throw new CustomException(GlobalSystemStatus.BAD_REQUEST);
        }
    }
}
