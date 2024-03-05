package com.example.storeweb.domain.board.repo;

import com.example.storeweb.domain.board.entity.UsedItemTradeOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardOrderRepository extends JpaRepository<UsedItemTradeOrderEntity,Long> {
}
