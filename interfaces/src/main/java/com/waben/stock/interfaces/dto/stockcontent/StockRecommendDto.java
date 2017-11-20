package com.waben.stock.interfaces.dto.stockcontent;

import java.util.Date;

/**
 * 股票推荐
 * 
 * @author luomengan
 *
 */
public class StockRecommendDto {

	private Long id;
	/**
	 * 股票ID
	 */
	private Long stockId;
	/**
	 * 股票代码
	 */
	private String code;

	/**
	 * 推荐时间
	 */
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStockId() {
		return stockId;
	}

	public void setStockId(Long stockId) {
		this.stockId = stockId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}



}
