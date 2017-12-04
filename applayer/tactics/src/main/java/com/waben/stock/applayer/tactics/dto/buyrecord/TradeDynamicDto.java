package com.waben.stock.applayer.tactics.dto.buyrecord;

import java.math.BigDecimal;

public class TradeDynamicDto {

	/**
	 * 发布人ID
	 */
	private Long publisherId;
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 交易类型，1表示买入，2表示卖出
	 */
	private Integer tradeType;
	/**
	 * 股票代码
	 */
	private String stockCode;
	/**
	 * 股票名称
	 */
	private String stockName;
	/**
	 * 盈利
	 */
	private BigDecimal profit;

	public Long getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
	}

	public String getPhone() {
		if (phone != null && phone.length() >= 8) {
			return phone.substring(0, 3) + "****" + phone.substring(7);
		}
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getTradeType() {
		return tradeType;
	}

	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public BigDecimal getProfit() {
		return profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}

}
