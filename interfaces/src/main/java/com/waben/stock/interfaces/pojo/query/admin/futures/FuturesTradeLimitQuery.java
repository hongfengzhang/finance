package com.waben.stock.interfaces.pojo.query.admin.futures;

import com.waben.stock.interfaces.pojo.query.PageAndSortQuery;

public class FuturesTradeLimitQuery extends PageAndSortQuery {

	private Long id;
	
	/**
	 * 合约ID
	 */
	private Long contractId;
	
	/**
	 * 合约代码
	 */
	private String symbol;
	/**
	 * 合约名称
	 */
	private String name;
	
	/**
	 * 限制类型
	 */
	private String limitType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
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

	public String getLimitType() {
		return limitType;
	}

	public void setLimitType(String limitType) {
		this.limitType = limitType;
	}
}
