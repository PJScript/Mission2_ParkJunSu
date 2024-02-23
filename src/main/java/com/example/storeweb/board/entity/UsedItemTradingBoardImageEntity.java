package com.example.storeweb.board.entity;

import com.example.storeweb.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class UsedItemTradingBoardImageEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id", referencedColumnName = "id")
    private UsedItemTradingBoardEntity board;
    private String sort;
    private String url;

    @Column(name = "file_view_name",nullable = false)
    private String fileViewName;

    @Column(name = "original_file_name",nullable = false)
    private String originalFileName;

    @Column(name = "is_thumbnail",nullable = false)
    private String isThumbnail;
    private String format;
    private String size;
}
