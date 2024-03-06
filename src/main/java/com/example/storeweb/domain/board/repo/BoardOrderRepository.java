package com.example.storeweb.domain.board.repo;

import com.example.storeweb.domain.board.entity.UsedItemTradeOrderEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface BoardOrderRepository extends JpaRepository<UsedItemTradeOrderEntity,Long> {

  /**
   * 내가보낸 요청 처리
   * */
  Page<UsedItemTradeOrderEntity> findAllByBoardIdAndTenantUuid(Pageable pageable, Long boardId, String uuid);


  Page<UsedItemTradeOrderEntity> findAllByBoardId(Pageable pageable, Long boardId);

  /** 타겟 요청 이외에 모든 요청은 거절 처리 */

  @Modifying
  @Query("UPDATE UsedItemTradeOrderEntity o SET o.status = (SELECT s FROM StatusEntity s WHERE s.value = 'PURCHASE_REQUEST_REFUSE') WHERE o.id != :acceptedOrderId")
  void refuseAllOrdersExcept(@Param("acceptedOrderId") Long acceptedOrderId);
}
