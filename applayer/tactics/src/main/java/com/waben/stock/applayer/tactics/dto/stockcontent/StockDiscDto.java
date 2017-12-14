package com.waben.stock.applayer.tactics.dto.stockcontent;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 股票盘口
 * 
 * @author luomengan
 *
 */
public class StockDiscDto {

	private String code;

	private String name;

	private BigDecimal yesterdayClosePrice;

	private BigDecimal highestPrice;

	private BigDecimal lowestPrice;

	private BigDecimal upDropPrice;

	private BigDecimal upDropSpeed;

	@SuppressWarnings("unused")
	private BigDecimal riseStopPrice;

	@SuppressWarnings("unused")
	private BigDecimal fallStopPrice;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getYesterdayClosePrice() {
		return yesterdayClosePrice;
	}

	public void setYesterdayClosePrice(BigDecimal yesterdayClosePrice) {
		this.yesterdayClosePrice = yesterdayClosePrice;
	}

	public BigDecimal getHighestPrice() {
		return highestPrice;
	}

	public void setHighestPrice(BigDecimal highestPrice) {
		this.highestPrice = highestPrice;
	}

	public BigDecimal getLowestPrice() {
		return lowestPrice;
	}

	public void setLowestPrice(BigDecimal lowestPrice) {
		this.lowestPrice = lowestPrice;
	}

	public BigDecimal getUpDropPrice() {
		return upDropPrice;
	}

	public void setUpDropPrice(BigDecimal upDropPrice) {
		this.upDropPrice = upDropPrice;
	}

	public BigDecimal getUpDropSpeed() {
		return upDropSpeed;
	}

	public void setUpDropSpeed(BigDecimal upDropSpeed) {
		this.upDropSpeed = upDropSpeed;
	}

	public BigDecimal getRiseStopPrice() {
		if (yesterdayClosePrice == null) {
			return null;
		}
		return yesterdayClosePrice.multiply(new BigDecimal(1.1)).setScale(2, RoundingMode.DOWN);
	}

	public BigDecimal getFallStopPrice() {
		if (yesterdayClosePrice == null) {
			return null;
		}
		return yesterdayClosePrice.multiply(new BigDecimal(0.9)).setScale(2, RoundingMode.DOWN);
	}

}
