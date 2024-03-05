package com.example.storeweb.domain.board.entity;

import com.example.storeweb.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Entity
@Table(name = "used_item_trading_board_image")
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UsedItemTradingBoardImageEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id", referencedColumnName = "id")
    private UsedItemTradingBoardEntity board;
    private Integer sort;
    private String url;

    @Column(name = "file_view_name",nullable = false)
    private String fileViewName;

    @Column(name = "original_file_name",nullable = false)
    private String originalFileName;

    @Column(name = "is_thumbnail",nullable = false)
    private boolean isThumbnail;
    private String format;
    private String size;
}
