package com.waben.stock.applayer.tactics.dto.futures;

import java.math.BigDecimal;

import com.waben.stock.interfaces.dto.futures.FuturesContractDto;

import io.swagger.annotations.ApiModelProperty;

/**
 * 期货合约和 行情 Dto
 * 
 * @author sl
 *
 */
public class FuturesContractQuotationDto extends FuturesContractDto {

	/**
	 * 最新价
	 */
	@ApiModelProperty(value = "最新价")
	private BigDecimal lastPrice;
	/**
	 * 跌涨价格
	 */
	@ApiModelProperty(value = "跌涨价格")
	private BigDecimal upDropPrice;
	/**
	 * 跌涨幅度
	 */
	@ApiModelProperty(value = "跌涨幅度")
	private BigDecimal upDropSpeed;
	/**
	 * 今天的开盘价
	 */
	@ApiModelProperty(value = "当天的开盘价")
	private BigDecimal openPrice;
	/**
	 * 当天最高价
	 */
	@ApiModelProperty(value = "当天最高价")
	private BigDecimal highPrice;
	/**
	 * 当天最低价
	 */
	@ApiModelProperty(value = "当天最低价")
	private BigDecimal lowPrice;
	/**
	 * 昨天的收盘价
	 */
	@ApiModelProperty(value = "昨天的收盘价")
	private BigDecimal closePrice;
	/**
	 * 当天成交量
	 */
	@ApiModelProperty(value = "当天成交量")
	private Integer volume;
	/**
	 * 品种分类名称
	 */
	@ApiModelProperty(value = "品种分类名称")
	private String productTypeName;

	public BigDecimal getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(BigDecimal lastPrice) {
		this.lastPrice = lastPrice;
	}

	public BigDecimal getUpDropSpeed() {
		return upDropSpeed;
	}

	public void setUpDropSpeed(BigDecimal upDropSpeed) {
		this.upDropSpeed = upDropSpeed;
	}

	public BigDecimal getClosePrice() {
		return closePrice;
	}

	public void setClosePrice(BigDecimal closePrice) {
		this.closePrice = closePrice;
	}

	public BigDecimal getUpDropPrice() {
		return upDropPrice;
	}

	public void setUpDropPrice(BigDecimal upDropPrice) {
		this.upDropPrice = upDropPrice;
	}

	public String getProductTypeName() {
		if (getProductType() != null) {
			return getProductType().getValue();
		}
		return productTypeName;
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

	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

}
