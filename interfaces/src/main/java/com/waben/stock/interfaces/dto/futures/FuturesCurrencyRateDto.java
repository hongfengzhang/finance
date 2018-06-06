package com.waben.stock.interfaces.dto.futures;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

public class FuturesCurrencyRateDto {

	private Long id;
	/**
	 * 货币，如“USD”
	 */
	@ApiModelProperty(value = "货币，如“USD”")
	private String currency;
	/**
	 * 货币名称，如“美元”
	 */
	@ApiModelProperty(value = "货币名称，如“美元”")
	private String currencyName;
	/**
	 * 汇率，如“7.0”，表示1美元=7.0*1人民币
	 */
	@ApiModelProperty(value = "汇率，如“7.0”，表示1美元=7.0*1人民币")
	private BigDecimal rate;

	/**
	 * 货币符号，如“$”,表示美元
	 */
	@ApiModelProperty(value = "货币符号，如“$”,表示美元")
	private String currencySign;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
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
