package com.waben.stock.datalayer.futures.repository.impl.jpa;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import com.waben.stock.datalayer.futures.entity.FuturesOrder;
import com.waben.stock.interfaces.enums.FuturesOrderType;

import feign.Param;

/**
 * 期货订单 Repository
 * 
 * @author sunl
 *
 */
public interface FuturesOrderRepository extends CustomJpaRepository<FuturesOrder, Long> {

	@Query(value = "SELECT count(*) FROM f_futures_order o LEFT JOIN f_futures_contract c ON o.contract_id = c.id  where c.id=?1 and o.order_type = ?2", nativeQuery = true)
	Integer countOrderByType(Long contractId, FuturesOrderType orderType);

	@Query(value = "select SUM(f.total_quantity) from f_futures_order f where f.contract_id = ?1 and f.publisher_id = ?2", nativeQuery = true)
	Integer sumByListOrderContractIdAndPublisherId(Long contractId, Long publisherId);
	
	@Query(value = "select * from f_futures_order where contract_term_id in ?1 and state != '8'", nativeQuery = true)
	List<FuturesOrder> findByContractTermId(@PathVariable("contractTermId") List<Long> contractTermId);

	@Query(value = "select * from f_futures_order where contract_id in ?1 and state != '8'", nativeQuery = true)
	List<FuturesOrder> findByContractId(@PathVariable("contractId") List<Long> contractId);
	/**
	 * 获取持仓中列表
	 * 
	 * @param publisherId
	 *            用户ID
	 * @return 持仓中列表
	 */
	@Query(value = "select * from f_futures_order f where f.publisher_id = ?1 and f.state = 5", nativeQuery = true)
	List<FuturesOrder> getListFuturesOrderPositionByPublisherId(@PathVariable("publisherId") Long publisherId);

	/**
	 * 获取持仓中总收益
	 * 
	 * @param publisherId
	 *            用户ID
	 * @return 持仓中总收益
	 */
	@Query(value = "select IF(sum(f.publisher_profit_or_loss) IS NULL,0,sum(f.publisher_profit_or_loss)) AS num from f_futures_order f where f.publisher_id = ?1 and f.state = 5", nativeQuery = true)
	BigDecimal settlementOrderPositionByPublisherId(@PathVariable("publisherId") Long publisherId);

	/**
	 * 获取委托中列表
	 * 
	 * @param publisherId
	 *            用户ID
	 * @return 委托中列表
	 */
	@Query(value = "select * from f_futures_order f where f.publisher_id = ?1 and f.state in( 3,4,6,7 )", nativeQuery = true)
	List<FuturesOrder> getListFuturesOrderEntrustByPublisherId(@PathVariable("publisherId") Long publisherId);

	/**
	 * 获取委托中总收益
	 * 
	 * @param publisherId
	 *            用户ID
	 * @return 委托中总收益
	 */
	@Query(value = "select IF(sum(f.publisher_profit_or_loss) IS NULL,0,sum(f.publisher_profit_or_loss)) AS num from f_futures_order f where f.publisher_id = ?1 and f.state in( 3,4,6,7 )", nativeQuery = true)
	BigDecimal settlementOrderEntrustByPublisherId(@PathVariable("publisherId") Long publisherId);

	/**
	 * 获取已结算列表
	 * 
	 * @param publisherId
	 *            用户ID
	 * @return 已结算列表
	 */
	@Query(value = "select * from f_futures_order f where f.publisher_id = ?1 and f.state = 8", nativeQuery = true)
	List<FuturesOrder> getListFuturesOrderUnwindByPublisherId(@PathVariable("publisherId") Long publisherId);

	/**
	 * 获取已结算总收益
	 * 
	 * @param publisherId
	 *            用户ID
	 * @return 已结算总收益
	 */
	@Query(value = "select IF(sum(f.publisher_profit_or_loss) IS NULL,0,sum(f.publisher_profit_or_loss)) AS num from f_futures_order f where f.publisher_id = ?1 and f.state = 8", nativeQuery = true)
	BigDecimal settlementOrderUnwindByPublisherId(@PathVariable("publisherId") Long publisherId);

	
}
