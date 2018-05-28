package com.waben.stock.interfaces.dto.futures;

import java.math.BigDecimal;

public class FuturesCurrencyRateDto {

	private Long id;
	/**
	 * 货币，如“USD”
	 */
	private String currency;
	/**
	 * 货币名称，如“美元”
	 */
	private String currencyName;
	/**
	 * 汇率，如“7.0”，表示1美元=7.0*1人民币
	 */
	private BigDecimal rate;
}
