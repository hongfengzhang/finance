package com.waben.stock.applayer.tactics.dto.futures;

import java.math.BigDecimal;

import com.waben.stock.interfaces.dto.futures.FuturesContractDto;

/**
 * 期货合约和 行情 Dto
 * 
 * @author sl
 *
 */
public class FuturesContractQuotationDto extends FuturesContractDto {

	/**
	 * 最新价
	 */
	private BigDecimal lastPrice;

	/**
	 * 涨跌幅
	 */
	private BigDecimal riseAndFall;

	public BigDecimal getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(BigDecimal lastPrice) {
		this.lastPrice = lastPrice;
	}

	public BigDecimal getRiseAndFall() {
		return riseAndFall;
	}

	public void setRiseAndFall(BigDecimal riseAndFall) {
		this.riseAndFall = riseAndFall;
	}

}
