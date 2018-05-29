package com.waben.stock.datalayer.futures.repository.impl.jpa;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.Query;

import com.waben.stock.datalayer.futures.entity.FuturesOrder;
import com.waben.stock.interfaces.enums.FuturesOrderType;

/**
 * 期货订单 Repository
 * 
 * @author sunl
 *
 */
public interface FuturesOrderRepository extends CustomJpaRepository<FuturesOrder, Long> {

	@Query(value = "SELECT count(*) FROM f_futures_order o LEFT JOIN f_futures_contract c ON o.contract_id = c.id  where c.id=?1 and o.order_type = ?2", nativeQuery = true)
	Integer countOrderByType(Long contractId, FuturesOrderType orderType);

	@Query("select SUM(f.totalQuantity) from FuturesOrder f where f.contract.id = ?1 and f.publisherId = ?2")
	BigDecimal sumByListOrderContractIdAndPublisherId(Long contractId, Long publisherId);
}
