package com.waben.stock.applayer.operation.service.fallback;

import com.waben.stock.applayer.operation.service.publisher.CapitalFlowService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.publisher.CapitalFlowDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.CapitalFlowQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class CapitalFlowServiceFallback implements CapitalFlowService{
    @Override
    public Response<PageInfo<CapitalFlowDto>> pagesByQuery(CapitalFlowQuery query) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

	@Override
	public Response<BigDecimal> promotionTotalAmount(Long publisherId) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}
}
