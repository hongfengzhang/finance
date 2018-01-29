package com.waben.stock.applayer.strategist.reference.fallback;

import org.springframework.stereotype.Component;

import com.waben.stock.applayer.strategist.reference.SettlementReference;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.buyrecord.SettlementDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.SettlementQuery;

/**
 * 点买记录 reference服务接口fallback
 *
 * @author luomengan
 */
@Component
public class SettlementReferenceFallback implements SettlementReference {

	@Override
	public Response<PageInfo<SettlementDto>> pagesByQuery(SettlementQuery query) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<SettlementDto> fetchByBuyRecord(Long id) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

}
