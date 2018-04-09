package com.waben.stock.applayer.promotion.reference.fallback;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import com.waben.stock.applayer.promotion.reference.organization.PriceMarkupConfigReference;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.organization.PriceMarkupConfigDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.form.organization.PriceMarkupForm;

/**
 * 加价配置 reference服务接口fallback
 *
 * @author luomengan
 */
@Component
public class PriceMarkupConfigReferenceFallback implements PriceMarkupConfigReference {

	@Override
	public Response<List<PriceMarkupConfigDto>> priceMarkupConfigList(Long orgId, Integer resourceType) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<String> priceMarkupConfig(List<PriceMarkupForm> configFormList) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<List<BigDecimal>> priceMarkupRatioList(Integer resourceType, Long resourceId, Long publisherId) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

}
