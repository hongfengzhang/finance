package com.waben.stock.datalayer.futures.repository.impl.jpa;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.waben.stock.datalayer.futures.entity.FuturesOrder;
import com.waben.stock.datalayer.futures.entity.FuturesOvernightRecord;

/**
 * 期货订单隔夜记录 Reopsitory
 * 
 * @author sunl
 *
 */
public interface FuturesOvernightRecordRepository extends CustomJpaRepository<FuturesOvernightRecord, Long> {

	List<FuturesOvernightRecord> findByOrder(FuturesOrder order, Sort sort);

}
