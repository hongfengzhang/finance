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
		return new Response<>("205", "暂无股票列表数据");
	}

	@Override
	public Response<StockDto> fetchById(Long id) {
		return new Response<>("205", "股票" + id + "信息不存在");
	}

	@Override
	public Response<StockDto> fetchByCode(String code) {
		return new Response<>("205", "股票" + code + "信息不存在");
	}

}
