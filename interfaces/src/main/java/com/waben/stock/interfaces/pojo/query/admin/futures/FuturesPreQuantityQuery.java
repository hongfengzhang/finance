package com.waben.stock.interfaces.pojo.query.admin.futures;

import java.util.List;

import com.waben.stock.interfaces.pojo.query.PageAndSortQuery;

public class FuturesPreQuantityQuery extends PageAndSortQuery {

	private Long id;
	
	private Long contractId;
	
	private List<Integer> quantity;

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

	public List<Integer> getQuantity() {
		return quantity;
	}

	public void setQuantity(List<Integer> quantity) {
		this.quantity = quantity;
	}
	
	
}
