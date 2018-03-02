package com.waben.stock.risk.service.fallback;

import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.stockcontent.StockDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StockQuery;
import com.waben.stock.risk.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Created by yuyidi on 2017/11/22.
 * @desc
 */
@Component
public class StockServiceFallback implements StockService {

	Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	public Response<PageInfo<StockDto>> pagesByQuery(StockQuery stockQuery) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<StockDto> fetchById(Long id) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<StockDto> fetchWithExponentByCode(String code) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<Integer> modify(StockDto stockDto) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public void delete(Long id) {

	}

	@Override
	public Response<StockDto> add(StockDto requestDto) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}
}
