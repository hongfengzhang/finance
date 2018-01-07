package com.waben.stock.applayer.strategist.retrivestock.bean;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 股票行情
 * 
 * @author luomengan
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockMarket {

	private String exchangeId;
	private String instrumentId;
	private BigDecimal lastPrice;
	private String name;
	private int status;
	private String tradeDay;
	private BigDecimal upDropPrice;
	private BigDecimal upDropSpeed;
	private long upTime;
	private String upTimeFormat;
	private BigDecimal highestPrice;
	private BigDecimal lowestPrice;
	private BigDecimal upLimitPrice;
	private BigDecimal downLimitPrice;
	/**
	 * 卖1~卖5
	 */
	private BigDecimal askPrice;
	private BigDecimal askPrice2;
	private BigDecimal askPrice3;
	private BigDecimal askPrice4;
	private BigDecimal askPrice5;
	/**
	 * 买1~买5
	 */
	private BigDecimal bidPrice;
	private BigDecimal bidPrice2;
	private BigDecimal bidPrice3;
	private BigDecimal bidPrice4;
	private BigDecimal bidPrice5;

	public String getExchangeId() {
		return exchangeId;
	}

	public void setExchangeId(String exchangeId) {
		this.exchangeId = exchangeId;
	}

	public String getInstrumentId() {
		return instrumentId;
	}

	public void setInstrumentId(String instrumentId) {
		this.instrumentId = instrumentId;
	}

	public BigDecimal getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(BigDecimal lastPrice) {
		this.lastPrice = lastPrice;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTradeDay() {
		return tradeDay;
	}

	public void setTradeDay(String tradeDay) {
		this.tradeDay = tradeDay;
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

	public long getUpTime() {
		return upTime;
	}

	public void setUpTime(long upTime) {
		this.upTime = upTime;
	}

	public String getUpTimeFormat() {
		return upTimeFormat;
	}

	public void setUpTimeFormat(String upTimeFormat) {
		this.upTimeFormat = upTimeFormat;
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

	public BigDecimal getAskPrice() {
		return askPrice;
	}

	public void setAskPrice(BigDecimal askPrice) {
		this.askPrice = askPrice;
	}

	public BigDecimal getUpLimitPrice() {
		return upLimitPrice;
	}

	public void setUpLimitPrice(BigDecimal upLimitPrice) {
		this.upLimitPrice = upLimitPrice;
	}

	public BigDecimal getDownLimitPrice() {
		return downLimitPrice;
	}

	public void setDownLimitPrice(BigDecimal downLimitPrice) {
		this.downLimitPrice = downLimitPrice;
	}

	public BigDecimal getAskPrice2() {
		return askPrice2;
	}

	public void setAskPrice2(BigDecimal askPrice2) {
		this.askPrice2 = askPrice2;
	}

	public BigDecimal getAskPrice3() {
		return askPrice3;
	}

	public void setAskPrice3(BigDecimal askPrice3) {
		this.askPrice3 = askPrice3;
	}

	public BigDecimal getAskPrice4() {
		return askPrice4;
	}

	public void setAskPrice4(BigDecimal askPrice4) {
		this.askPrice4 = askPrice4;
	}

	public BigDecimal getAskPrice5() {
		return askPrice5;
	}

	public void setAskPrice5(BigDecimal askPrice5) {
		this.askPrice5 = askPrice5;
	}

	public BigDecimal getBidPrice() {
		return bidPrice;
	}

	public void setBidPrice(BigDecimal bidPrice) {
		this.bidPrice = bidPrice;
	}

	public BigDecimal getBidPrice2() {
		return bidPrice2;
	}

	public void setBidPrice2(BigDecimal bidPrice2) {
		this.bidPrice2 = bidPrice2;
	}

	public BigDecimal getBidPrice3() {
		return bidPrice3;
	}

	public void setBidPrice3(BigDecimal bidPrice3) {
		this.bidPrice3 = bidPrice3;
	}

	public BigDecimal getBidPrice4() {
		return bidPrice4;
	}

	public void setBidPrice4(BigDecimal bidPrice4) {
		this.bidPrice4 = bidPrice4;
	}

	public BigDecimal getBidPrice5() {
		return bidPrice5;
	}

	public void setBidPrice5(BigDecimal bidPrice5) {
		this.bidPrice5 = bidPrice5;
	}

}
