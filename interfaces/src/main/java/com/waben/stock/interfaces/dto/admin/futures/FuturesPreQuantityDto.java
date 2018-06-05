package com.waben.stock.interfaces.dto.admin.futures;

public class FuturesPreQuantityDto {

	private Long id;
	
	/**
	 * 对应的合约id
	 */
	private Long contractId;
	
	/**
	 * 手数
	 */
	private Integer quantity;

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

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
