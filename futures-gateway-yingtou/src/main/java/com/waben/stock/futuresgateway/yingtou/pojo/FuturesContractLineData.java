package com.waben.stock.futuresgateway.yingtou.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class FuturesContractLineData {

	/**
	 * 时间
	 */
	private Date time;
	/**
	 * 开盘
	 */
	private BigDecimal open;
	/**
	 * 最高
	 */
	private BigDecimal high;
	/**
	 * 最低
	 */
	private BigDecimal low;
	/**
	 * 收盘
	 */
	private BigDecimal close;
	/**
	 * 交易量
	 */
	private Integer volume;
	/**
	 * 总数?
	 */
	private Integer count;

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public BigDecimal getOpen() {
		return open;
	}

	public void setOpen(BigDecimal open) {
		this.open = open;
	}

	public BigDecimal getHigh() {
		return high;
	}

	public void setHigh(BigDecimal high) {
		this.high = high;
	}

	public BigDecimal getLow() {
		return low;
	}

	public void setLow(BigDecimal low) {
		this.low = low;
	}

	public BigDecimal getClose() {
		return close;
	}

	public void setClose(BigDecimal close) {
		this.close = close;
	}

	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
