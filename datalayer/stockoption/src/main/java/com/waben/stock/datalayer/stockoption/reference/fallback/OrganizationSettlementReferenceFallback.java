package com.waben.stock.datalayer.stockoption.reference.fallback;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.waben.stock.datalayer.stockoption.reference.OrganizationSettlementReference;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;

@Component
public class OrganizationSettlementReferenceFallback implements OrganizationSettlementReference {

	@Override
	public Response<String> strategySettlement(Long publisherId, Long buyRecordId, String tradeNo, Long strategyTypeId,
			BigDecimal serviceFee, BigDecimal deferredFee) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<String> stockoptionSettlement(Long publisherId, Long stockOptionTradeId, String tradeNo,
			Long cycleId, BigDecimal rightMoneyProfit) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

}
