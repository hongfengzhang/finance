package com.waben.stock.applayer.strategist.reference.fallback;

import org.springframework.stereotype.Component;

import com.waben.stock.applayer.strategist.reference.StockOptionQuoteReference;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.stockoption.StockOptionQuoteDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;

/**
 * 期权报价 reference服务接口fallback
 *
 * @author luomengan
 */
@Component
public class StockOptionQuoteReferenceFallback implements StockOptionQuoteReference {

	@Override
	public Response<StockOptionQuoteDto> quote(String stockCode, Integer cycle) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

}
