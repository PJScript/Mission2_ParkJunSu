package com.example.storeweb.domain.board.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/board")
public class BoardController {

    @GetMapping("/{name}")
    public void getBoardData(
            @RequestParam
            String name
    ){
        // TODO: {name} 게시판의 내용을 조회 ( 활성 사용자만 조회할 수 있음 )
    }
}
