package com.example.storeweb.domain.board.repo;

import com.example.storeweb.domain.board.entity.UsedItemTradeOrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface BoardOrderRepository extends JpaRepository<UsedItemTradeOrderEntity,Long> {
  Page<UsedItemTradeOrderEntity> findAllByBoardItemIdAndTenantUuid(Pageable pageable, Long boardItemId, String uuid);
  Page<UsedItemTradeOrderEntity> findAllByBoardItemId(Pageable pageable, Long boardItemId);

}
