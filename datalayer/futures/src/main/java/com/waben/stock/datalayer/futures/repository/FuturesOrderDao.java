package com.waben.stock.datalayer.futures.repository;

import com.waben.stock.datalayer.futures.entity.FuturesOrder;


/**

 * @author  pengzhenliang

 * @create  2018/3/3 15:29

 * @desc

 **/
public interface FuturesOrderDao extends BaseDao<FuturesOrder, Long> {

	
	Integer countStockOptionTradeState(Long publisherId);
}
