package com.waben.stock.interfaces.pojo.query.admin.futures;

import com.waben.stock.interfaces.pojo.query.PageAndSortQuery;

public class FuturesContractAdminQuery extends PageAndSortQuery {

	/**
	 * 交易ID
	 */
	private Long id;
	
	/**
	 * 交易所代码
	 */
	private String exchangcode;
	
	/**
	 * 交易所全称
	 */
	private String exchangename;
	
	/**
	 * 交易所类型
	 * <ul>
	 * <li>1外盘</li>
	 * <li>2内盘</li>
	 * </ul>
	 */
	private Integer exchangeType;
	
	/**
	 * 产品代码
	 */
	private String symbol;
	
	/**
	 * 产品名称
	 */
	private String name;
	
	/**
	 * 产品分类
	 */
	private String productType;
	
	/**
	 * 查询类型
	 */
	private Integer queryType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExchangcode() {
		return exchangcode;
	}

	public void setExchangcode(String exchangcode) {
		this.exchangcode = exchangcode;
	}

	public String getExchangename() {
		return exchangename;
	}

	public void setExchangename(String exchangename) {
		this.exchangename = exchangename;
	}

	public Integer getExchangeType() {
		return exchangeType;
	}

	public void setExchangeType(Integer exchangeType) {
		this.exchangeType = exchangeType;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Integer getQueryType() {
		return queryType;
	}

	public void setQueryType(Integer queryType) {
		this.queryType = queryType;
	}

	

}
