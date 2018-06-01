package com.waben.stock.datalayer.futures.repository.impl.jpa;

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

	@Query(value = "SELECT IF(SUM(s.num) IS NULL,0,SUM(s.num)) AS  user_num FROM ("
			+ "SELECT IF(f.order_type = 1,f.total_quantity,-f.total_quantity) as num FROM f_futures_order f where f.state in(2,4,5,6,7,8) AND f.publisher_id= ?2 AND f.contract_id = ?1) s", nativeQuery = true)
	Integer sumByListOrderContractIdAndPublisherId(Long contractId, Long publisherId);
}
