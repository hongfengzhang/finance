package com.waben.stock.futuresgateway.yisheng.pojo;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FuturesContractLineData implements Comparable<FuturesContractLineData> {

	/** 品种编号 */
	private String commodityNo;
	/** 合约编号 */
	private String contractNo;
	/** 时间 */
	private Date time;
	/** 时间字符串 */
	private String timeStr;
	/** 开盘价 */
	private BigDecimal openPrice;
	/** 最高价 */
	private BigDecimal highPrice;
	/** 最低价 */
	private BigDecimal lowPrice;
	/** 收盘价 */
	private BigDecimal closePrice;
	/** 成交量 */
	private long volume;
	/** 当天总成交量 */
	private long totalVolume;

	public String getCommodityNo() {
		return commodityNo;
	}

	public void setCommodityNo(String commodityNo) {
		this.commodityNo = commodityNo;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getTimeStr() {
		return timeStr;
	}

	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}

	public BigDecimal getOpenPrice() {
		return openPrice;
	}

	public void setOpenPrice(BigDecimal openPrice) {
		this.openPrice = openPrice;
	}

	public BigDecimal getHighPrice() {
		return highPrice;
	}

	public void setHighPrice(BigDecimal highPrice) {
		this.highPrice = highPrice;
	}

	public BigDecimal getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(BigDecimal lowPrice) {
		this.lowPrice = lowPrice;
	}

	public BigDecimal getClosePrice() {
		return closePrice;
	}

	public void setClosePrice(BigDecimal closePrice) {
		this.closePrice = closePrice;
	}

	public long getVolume() {
		return volume;
	}

	public void setVolume(long volume) {
		this.volume = volume;
	}

	public long getTotalVolume() {
		return totalVolume;
	}

	public void setTotalVolume(long totalVolume) {
		this.totalVolume = totalVolume;
	}

	@Override
	public int compareTo(FuturesContractLineData o) {
		return timeStr.compareTo(o.getTimeStr());
	}

}
