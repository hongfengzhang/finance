package com.waben.stock.interfaces.pojo.query.admin.futures;

import java.sql.Date;

import com.waben.stock.interfaces.pojo.query.PageAndSortQuery;

public class FuturesExchangeAdminQuery extends PageAndSortQuery {

	/**
	 * 交易所代码
	 */
	private String code;
	/**
	 * 交易所全称
	 */
	private String name;
	/**
	 * 交易所类型
	 * <ul>
	 * <li>1外盘</li>
	 * <li>2内盘</li>
	 * </ul>
	 */
	private Integer exchangeType;
	
	/**
	 * 是否可用
	 */
	private Boolean enable;
	
	/**
	 * 北京时间的时差和交易所
	 */
	private Integer timeZoneGap;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getExchangeType() {
		return exchangeType;
	}

	public void setExchangeType(Integer exchangeType) {
		this.exchangeType = exchangeType;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public Integer getTimeZoneGap() {
		return timeZoneGap;
	}

	public void setTimeZoneGap(Integer timeZoneGap) {
		this.timeZoneGap = timeZoneGap;
	}

}
