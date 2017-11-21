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
	 * 股票名称
	 */
	private String name;
	/**
	 * 股票代码
	 */
	private String code;
	/**
	 * 拼音缩写，股票名称的首字母
	 */
	private String pinyinAbbr;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 推荐时间
	 */
	private Date recommendTime;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPinyinAbbr() {
		return pinyinAbbr;
	}

	public void setPinyinAbbr(String pinyinAbbr) {
		this.pinyinAbbr = pinyinAbbr;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Date getRecommendTime() {
		return recommendTime;
	}

	public void setRecommendTime(Date recommendTime) {
		this.recommendTime = recommendTime;
	}

}
