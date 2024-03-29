package com.example.storeweb.domain.board.controller;

import com.example.storeweb.common.GlobalSystemStatus;
import com.example.storeweb.domain.board.dto.PostResponse;
import com.example.storeweb.domain.board.dto.BoardProductResponse;
import com.example.storeweb.domain.board.entity.UsedItemTradingBoardEntity;
import com.example.storeweb.domain.board.dto.BoardItem;
import com.example.storeweb.domain.board.service.BoardService;
import com.example.storeweb.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/board")
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/trade")
    public ResponseEntity<List<PostResponse>> getBoardData(
            // page, size
            Pageable pageable


    ) {

        Page<UsedItemTradingBoardEntity> entities = boardService.findPost(pageable);
        List<PostResponse> dtos = entities.stream()
                .map(PostResponse::convertToDto)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(
                dtos
        );

    }


    @PostMapping("/product/add")
    public ResponseEntity<BoardProductResponse> addBoardData(
            @RequestPart(value = "thumbnail", required = false)
            MultipartFile file,
            @RequestPart
            BoardItem
                    dto
    ) {


        try {
            UsedItemTradingBoardEntity entity = boardService.productAdd(file, dto);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(BoardProductResponse.builder()
                            .postId(entity.getId())
                            .build());

        } catch (IOException e) {
            // 임시
            throw new CustomException(GlobalSystemStatus.BAD_REQUEST);
        }


    }


    @PutMapping("/product/{id}/update")
    public ResponseEntity<BoardProductResponse> updateBoardData(
            @PathVariable("id")
            Long id,
            @RequestBody
            BoardItem dto) {
        UsedItemTradingBoardEntity entity = boardService.updatePost(dto, id);

        return ResponseEntity.status(HttpStatus.OK).body(BoardProductResponse.builder()
                .postId(entity.getId()).build()
        );

    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Object> deleteBoardDate(
            @PathVariable("id")
            Long id
    ){
        log.info(id + " TEST ");
        boardService.deletePost(id);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }


}
