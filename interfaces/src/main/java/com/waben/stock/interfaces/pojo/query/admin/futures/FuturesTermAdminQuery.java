package com.waben.stock.interfaces.pojo.query.admin.futures;

import com.waben.stock.interfaces.pojo.query.PageAndSortQuery;

public class FuturesTermAdminQuery extends PageAndSortQuery {

private Long id;
	
//	/**
//	 * 交易所代码
//	 */
//	private String exchangcode;
//	
//	/**
//	 * 交易所全称
//	 */
//	private String exchangename;
	
	/**
	 * 产品代码
	 */
	private String symbol;
	
	/**
	 * 产品名称
	 */
	private String name;
	
	/**
	 * 合约期限编号
	 */
	private String contractNo;
	
	/**
	 * 是否为当前实施的期限
	 */
	private boolean current;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

//	public String getExchangcode() {
//		return exchangcode;
//	}
//
//	public void setExchangcode(String exchangcode) {
//		this.exchangcode = exchangcode;
//	}
//
//	public String getExchangename() {
//		return exchangename;
//	}
//
//	public void setExchangename(String exchangename) {
//		this.exchangename = exchangename;
//	}

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

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public boolean isCurrent() {
		return current;
	}

	public void setCurrent(boolean current) {
		this.current = current;
	}
}
