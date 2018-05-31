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
	private BigDecimal lastPrice;
	/**
	 * 跌涨价格
	 */
	private BigDecimal upDropPrice;
	/**
	 * 跌涨幅度
	 */
	private BigDecimal upDropSpeed;
	/**
	 * 昨收
	 */
	private BigDecimal closePrice;
	/**
	 * 品种分类名称
	 */
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

}
