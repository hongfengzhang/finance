package com.waben.stock.applayer.tactics.service.fallback;

import org.springframework.stereotype.Component;

import com.waben.stock.interfaces.dto.stockcontent.StockDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StockQuery;
import com.waben.stock.interfaces.service.stockcontent.StockInterface;

/**
 * 轮播 断路器回调
 * 
 * @author luomengan
 *
 */
@Component
public class StockServiceFallback implements StockInterface {

	@Override
	public Response<PageInfo<StockDto>> pagesByQuery(StockQuery stockQuery) {
		// TODO Auto-generated method stub
		return null;
	}

}
