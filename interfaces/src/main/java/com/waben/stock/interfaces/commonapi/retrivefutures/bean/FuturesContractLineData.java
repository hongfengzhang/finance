package com.waben.stock.interfaces.commonapi.retrivefutures.bean;

import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 期货合约历史图数据（分时、日K、分钟K）
 * 
 * @author luomengan
 *
 */
@ApiModel(description = "期货合约历史图数据（分时、日K、分钟K）")
public class FuturesContractLineData {

	/**
	 * 时间
	 */
	@ApiModelProperty(value = "时间")
	private Date time;
	/**
	 * 开盘
	 */
	@ApiModelProperty(value = "开盘")
	private BigDecimal open;
	/**
	 * 最高
	 */
	@ApiModelProperty(value = "最高")
	private BigDecimal high;
	/**
	 * 最低
	 */
	@ApiModelProperty(value = "最低")
	private BigDecimal low;
	/**
	 * 收盘
	 */
	@ApiModelProperty(value = "收盘")
	private BigDecimal close;
	/**
	 * 交易量
	 */
	@ApiModelProperty(value = "交易量")
	private Integer volume;
	/**
	 * 总交易量?
	 */
	@ApiModelProperty(value = "总交易量?")
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
		if (volume == null || volume < 0) {
			return 0;
		}
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

	public Integer getCount() {
		if (count == null || count < 0) {
			return 0;
		}
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
