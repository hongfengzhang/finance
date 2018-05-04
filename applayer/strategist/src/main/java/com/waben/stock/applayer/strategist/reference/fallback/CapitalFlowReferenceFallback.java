package com.waben.stock.applayer.strategist.reference.fallback;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.waben.stock.applayer.strategist.reference.CapitalFlowReference;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.admin.publisher.CapitalFlowAdminDto;
import com.waben.stock.interfaces.dto.publisher.CapitalFlowDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.CapitalFlowQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.admin.publisher.CapitalFlowAdminQuery;

/**
 * 资金流水 reference服务接口fallback
 * 
 * @author luomengan
 *
 */
@Component
public class CapitalFlowReferenceFallback implements CapitalFlowReference {

	@Override
	public Response<PageInfo<CapitalFlowDto>> pagesByQuery(CapitalFlowQuery query) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<BigDecimal> promotionTotalAmount(Long publisherId) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<CapitalFlowDto> fetchById(Long capitalFlowId) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<PageInfo<CapitalFlowAdminDto>> adminPagesByQuery(CapitalFlowAdminQuery query) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<BigDecimal> adminAccumulateAmountByQuery(CapitalFlowAdminQuery query) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

}
