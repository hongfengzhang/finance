package com.waben.stock.applayer.operation.service.fallback;

import com.waben.stock.applayer.operation.service.stock.StrategyTypeService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.stockcontent.StrategyTypeDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StrategyTypeQuery;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Created by yuyidi on 2017/12/6.
 * @desc
 */
@Component
public class StrategyTypeServiceFallback implements StrategyTypeService {
	@Override
	public Response<List<StrategyTypeDto>> lists(Boolean enable) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<PageInfo<StrategyTypeDto>> pages(StrategyTypeQuery query) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<StrategyTypeDto> modify(StrategyTypeDto strategyTypeDto) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public void delete(Long id) {

	}

	@Override
	public Response<StrategyTypeDto> fetchById(Long id) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}
}
