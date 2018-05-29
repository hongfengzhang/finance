package com.waben.stock.datalayer.futures.repository;

import java.math.BigDecimal;

import com.waben.stock.datalayer.futures.entity.FuturesOrder;
import com.waben.stock.interfaces.enums.FuturesOrderState;
import com.waben.stock.interfaces.enums.FuturesOrderType;

/**
 * 期货订单 Dao
 * 
 * @author sunl
 *
 */
public interface FuturesOrderDao extends BaseDao<FuturesOrder, Long> {

	FuturesOrder editOrder(Long id, FuturesOrderState state);

	Integer countOrderByType(Long contractId, FuturesOrderType orderType);

	/**
	 * 根据合约ID和用户ID获取用户购买该合约总数
	 * 
	 * @param contractId
	 *            合约ID
	 * @param publisherId
	 *            用户ID
	 * @return 合约总数
	 */
	BigDecimal sumByListOrderContractIdAndPublisherId(Long contractId, Long publisherId);
}
