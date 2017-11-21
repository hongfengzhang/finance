package com.waben.stock.interfaces.dto.publisher;

import java.util.Date;

public class FavoriteStockDto {

	private Long id;
	/**
	 * 策略发布人序列号
	 */
	private String publisherSerialCode;
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
	 * 收藏时间
	 */
	private Date favoriteTime;
	/**
	 * 排序
	 */
	private Integer sort;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPublisherSerialCode() {
		return publisherSerialCode;
	}

	public void setPublisherSerialCode(String publisherSerialCode) {
		this.publisherSerialCode = publisherSerialCode;
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

	public Date getFavoriteTime() {
		return favoriteTime;
	}

	public void setFavoriteTime(Date favoriteTime) {
		this.favoriteTime = favoriteTime;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
