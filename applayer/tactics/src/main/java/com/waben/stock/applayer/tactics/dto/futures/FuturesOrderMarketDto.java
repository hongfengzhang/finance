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

	/**
	 * 订单类型描述
	 */
	private String buyOrderTypeDesc;

	/**
	 * 浮动盈亏
	 */
	private BigDecimal floatingProfitOrLoss;

	/**
	 * 汇率，如“7.0”，表示1美元=7.0*1人民币
	 */
	private BigDecimal rate;

	public BigDecimal getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(BigDecimal lastPrice) {
		this.lastPrice = lastPrice;
	}

	public String getBuyOrderTypeDesc() {
		return buyOrderTypeDesc;
	}

	public void setBuyOrderTypeDesc(String buyOrderTypeDesc) {
		this.buyOrderTypeDesc = buyOrderTypeDesc;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getFloatingProfitOrLoss() {
		return floatingProfitOrLoss;
	}

	public void setFloatingProfitOrLoss(BigDecimal floatingProfitOrLoss) {
		this.floatingProfitOrLoss = floatingProfitOrLoss;
	}

}
