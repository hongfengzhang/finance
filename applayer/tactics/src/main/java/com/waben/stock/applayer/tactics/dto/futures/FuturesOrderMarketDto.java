package com.waben.stock.applayer.tactics.dto.futures;

import java.math.BigDecimal;

import com.waben.stock.interfaces.dto.futures.FuturesOrderDto;

/**
 * 期货订单与行情Dto
 * 
 * @author sl
 *
 */
public class FuturesOrderMarketDto extends FuturesOrderDto {

	/**
	 * 最新价
	 */
	private BigDecimal lastPrice;

	public BigDecimal getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(BigDecimal lastPrice) {
		this.lastPrice = lastPrice;
	}

}
