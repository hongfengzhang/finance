package com.waben.stock.interfaces.service.organization;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.waben.stock.interfaces.pojo.Response;

public interface OrganizationSettlementInterface {

	@RequestMapping(value = "/strategysettlement/{publisherId}/{buyRecordId}/{tradeNo}/{strategyTypeId}/{serviceFee}/{deferredFee}", method = RequestMethod.POST)
	public Response<String> strategySettlement(@PathVariable("publisherId") Long publisherId,
			@PathVariable("buyRecordId") Long buyRecordId, @PathVariable("tradeNo") String tradeNo,
			@PathVariable("strategyTypeId") Long strategyTypeId, @PathVariable("serviceFee") BigDecimal serviceFee,
			@PathVariable("deferredFee") BigDecimal deferredFee);

	@RequestMapping(value = "stockoptionsettlement/{publisherId}/{stockOptionTradeId}/{tradeNo}/{cycleId}/{rightMoneyProfit}", method = RequestMethod.POST)
	public Response<String> stockoptionSettlement(@PathVariable("publisherId") Long publisherId,
			@PathVariable("stockOptionTradeId") Long stockOptionTradeId, @PathVariable("tradeNo") String tradeNo,
			@PathVariable("cycleId") Long cycleId, @PathVariable("rightMoneyProfit") BigDecimal rightMoneyProfit);

}
