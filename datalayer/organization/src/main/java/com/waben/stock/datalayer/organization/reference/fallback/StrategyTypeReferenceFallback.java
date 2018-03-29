package com.waben.stock.datalayer.organization.reference.fallback;

import java.util.List;

import org.springframework.stereotype.Component;

import com.waben.stock.datalayer.organization.reference.StrategyTypeReference;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.stockcontent.StrategyTypeDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StrategyTypeQuery;

/**
 * 策略类型 reference服务接口fallback
 *
 * @author luomengan
 */
@Component
public class StrategyTypeReferenceFallback implements StrategyTypeReference {

	@Override
	public Response<StrategyTypeDto> fetchById(Long id) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<List<StrategyTypeDto>> lists(Boolean enable) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<PageInfo<StrategyTypeDto>> pages(StrategyTypeQuery query) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<StrategyTypeDto> modify(StrategyTypeDto strategyTypeDto, List<Long> loss) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public void delete(Long id) {

	}

	@Override
	public Response<StrategyTypeDto> add(StrategyTypeDto requestDto) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

}
