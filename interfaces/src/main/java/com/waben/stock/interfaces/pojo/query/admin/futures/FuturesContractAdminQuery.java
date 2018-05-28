package com.waben.stock.interfaces.pojo.query.admin.futures;

import java.sql.Date;

import com.waben.stock.interfaces.pojo.query.PageAndSortQuery;

public class FuturesContractAdminQuery extends PageAndSortQuery {

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
	 * 开始时间
	 */
	private Date startTime;
	
	/**
	 * 截止时间
	 */
	private Date endTime;
}
