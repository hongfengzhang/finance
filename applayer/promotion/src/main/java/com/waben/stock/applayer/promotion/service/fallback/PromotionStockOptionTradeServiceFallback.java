package com.waben.stock.applayer.promotion.service.fallback;

import org.springframework.stereotype.Component;

import com.waben.stock.applayer.promotion.service.organization.PromotionStockOptionTradeService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.organization.PromotionStockOptionTradeDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.organization.PromotionStockOptionTradeQuery;

/**
 * 推广渠道产生的期权交易 reference服务接口fallback
 *
 * @author luomengan
 */
@Component
public class PromotionStockOptionTradeServiceFallback implements PromotionStockOptionTradeService {

	@Override
	public Response<PageInfo<PromotionStockOptionTradeDto>> adminPage(PromotionStockOptionTradeQuery query) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

}
