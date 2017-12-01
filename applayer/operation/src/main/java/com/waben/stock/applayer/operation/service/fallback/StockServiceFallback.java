package com.waben.stock.applayer.operation.service.fallback;

import org.springframework.stereotype.Component;

import com.waben.stock.applayer.operation.service.stock.StockService;
import com.waben.stock.interfaces.dto.stockcontent.StockDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StockQuery;

/**
 * @author Created by yuyidi on 2017/11/22.
 * @desc
 */
@Component
public class StockServiceFallback implements StockService {
	@Override
	public Response<PageInfo<StockDto>> pagesByQuery(StockQuery stockQuery) {
		return new Response<>("205", "暂无股票列表数据");
	}

	@Override
	public Response<StockDto> fetchById(Long id) {
		return new Response<>("205", "股票" + id + "信息不存在");
	}
}
