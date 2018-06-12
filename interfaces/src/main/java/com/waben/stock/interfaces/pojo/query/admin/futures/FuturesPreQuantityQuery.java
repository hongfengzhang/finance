package com.waben.stock.interfaces.pojo.query.admin.futures;

import java.util.List;

import com.waben.stock.interfaces.pojo.query.PageAndSortQuery;

public class FuturesPreQuantityQuery extends PageAndSortQuery {

	private Long id;
	
	private Long commodityId;
	
	private String quantity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public Long getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(Long commodityId) {
		this.commodityId = commodityId;
	}

 	
	
}
