package com.example.storeweb.domain.board.repo;

import com.example.storeweb.domain.board.entity.UsedItemTradingBoardImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardImageRepository extends JpaRepository<UsedItemTradingBoardImageEntity,Long> {
}
