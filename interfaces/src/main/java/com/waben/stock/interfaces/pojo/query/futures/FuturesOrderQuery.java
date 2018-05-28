package com.waben.stock.interfaces.pojo.query.futures;

import com.waben.stock.interfaces.enums.FuturesOrderState;
import com.waben.stock.interfaces.pojo.query.PageAndSortQuery;

import io.swagger.annotations.ApiModelProperty;

public class FuturesOrderQuery extends PageAndSortQuery {

	@ApiModelProperty(value = "用户ID")
	private Long publisherId;

	@ApiModelProperty(value = "期货订单状态")
	private FuturesOrderState state;

	public FuturesOrderQuery() {
		super();
	}

	public FuturesOrderQuery(int page, int size) {
		super();
		super.setPage(page);
		super.setSize(size);
	}

	public FuturesOrderState getState() {
		return state;
	}

	public void setState(FuturesOrderState state) {
		this.state = state;
	}

	public Long getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
	}

}
