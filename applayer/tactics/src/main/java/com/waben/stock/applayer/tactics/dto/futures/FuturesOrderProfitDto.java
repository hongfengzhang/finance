package com.waben.stock.applayer.tactics.dto.futures;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

public class FuturesOrderProfitDto {

	/**
	 * 盈亏
	 */
	@ApiModelProperty(value = "盈亏")
	private BigDecimal totalIncome;
	/**
	 * 汇率
	 */
	@ApiModelProperty(value = "汇率")
	private BigDecimal rate;
	/**
	 * 货币符号
	 */
	@ApiModelProperty(value = "货币符号，如￥、$")
	private String currencySign;

	public BigDecimal getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(BigDecimal totalIncome) {
		this.totalIncome = totalIncome;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String getCurrencySign() {
		return currencySign;
	}

	public void setCurrencySign(String currencySign) {
		this.currencySign = currencySign;
	}

}
