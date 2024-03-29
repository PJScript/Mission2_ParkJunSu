package com.example.storeweb.domain.board.repo;

import com.example.storeweb.domain.board.entity.UsedItemTradingBoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<UsedItemTradingBoardEntity, Long> {

    Page<UsedItemTradingBoardEntity> findAll(Pageable pageable);
}
