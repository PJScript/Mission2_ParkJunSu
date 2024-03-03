package com.example.storeweb.domain.board.repo;

import com.example.storeweb.domain.board.entity.UsedItemTradingBoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<UsedItemTradingBoardEntity, Long> {
}
