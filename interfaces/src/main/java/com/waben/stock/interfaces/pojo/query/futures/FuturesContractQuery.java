package com.waben.stock.interfaces.pojo.query.futures;

import com.waben.stock.interfaces.pojo.query.PageAndSortQuery;

import io.swagger.annotations.ApiModelProperty;

public class FuturesContractQuery extends PageAndSortQuery {

	@ApiModelProperty(value = "合约ID")
	private Long contractId;

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

}
