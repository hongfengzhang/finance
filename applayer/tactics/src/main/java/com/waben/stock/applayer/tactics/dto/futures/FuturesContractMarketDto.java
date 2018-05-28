package com.waben.stock.applayer.tactics.dto.futures;

import java.math.BigDecimal;

import com.waben.stock.interfaces.dto.futures.FuturesContractDto;

/**
 * 期货合约和 行情 Dto
 * 
 * @author sl
 *
 */
public class FuturesContractMarketDto extends FuturesContractDto {

	/**
	 * 最新价
	 */
	private BigDecimal lastPrice;

	/**
	 * 买量
	 */
	private Integer buyNum;

	/**
	 * 卖量
	 */
	private Integer sellNum;

	/**
	 * 涨跌幅
	 */
	private BigDecimal riseAndFall;

	/**
	 * 涨跌量
	 */
	private BigDecimal riseFallNum;

	public BigDecimal getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(BigDecimal lastPrice) {
		this.lastPrice = lastPrice;
	}

	public Integer getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(Integer buyNum) {
		this.buyNum = buyNum;
	}

	public Integer getSellNum() {
		return sellNum;
	}

	public void setSellNum(Integer sellNum) {
		this.sellNum = sellNum;
	}

	public BigDecimal getRiseAndFall() {
		return riseAndFall;
	}

	public void setRiseAndFall(BigDecimal riseAndFall) {
		this.riseAndFall = riseAndFall;
	}

	public BigDecimal getRiseFallNum() {
		return riseFallNum;
	}

	public void setRiseFallNum(BigDecimal riseFallNum) {
		this.riseFallNum = riseFallNum;
	}

}
