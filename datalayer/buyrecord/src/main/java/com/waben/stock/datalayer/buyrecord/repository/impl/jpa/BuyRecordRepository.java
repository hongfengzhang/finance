package com.waben.stock.datalayer.buyrecord.repository.impl.jpa;

import com.waben.stock.datalayer.buyrecord.entity.BuyRecord;
import com.waben.stock.interfaces.enums.BuyRecordState;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 点买记录 Jpa
 * 
 * @author luomengan
 *
 */
public interface BuyRecordRepository extends CustomJpaRepository<BuyRecord, Long> {

    @Query("select b from BuyRecord b where b.state = :buyRecordState order by b.createTime desc ")
    List<BuyRecord> findAllByStateAndOrderByCreateTime(@Param("buyRecordState") BuyRecordState buyRecordState);
}
