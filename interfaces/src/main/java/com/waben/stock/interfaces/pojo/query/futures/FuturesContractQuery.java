package com.waben.stock.interfaces.pojo.query.futures;

import com.waben.stock.interfaces.pojo.query.PageAndSortQuery;

import io.swagger.annotations.ApiModelProperty;

public class FuturesContractQuery extends PageAndSortQuery {

	@ApiModelProperty(value = "合约ID")
	private Long contractId;

	@ApiModelProperty(value = "用户ID")
	private Long publisherId;

	@ApiModelProperty(value = "合约代码")
	private String symbol;

	@ApiModelProperty(value = "是否app合约")
	private Boolean appContract;

	@ApiModelProperty(value = "是否pc合约")
	private Boolean pcContract;

	public FuturesContractQuery() {
		super();
	}

	public FuturesContractQuery(int page, int size) {
		super();
		super.setPage(page);
		super.setSize(size);
	}

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	public Long getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Boolean getAppContract() {
		return appContract;
	}

	public void setAppContract(Boolean appContract) {
		this.appContract = appContract;
	}

	public Boolean getPcContract() {
		return pcContract;
	}

	public void setPcContract(Boolean pcContract) {
		this.pcContract = pcContract;
	}

}
