package com.waben.stock.interfaces.pojo.query.futures;

import com.waben.stock.interfaces.enums.FuturesOrderState;
import com.waben.stock.interfaces.pojo.query.PageAndSortQuery;

import io.swagger.annotations.ApiModelProperty;

public class FuturesOrderQuery extends PageAndSortQuery {

	@ApiModelProperty(value = "用户ID")
	private Long publisherId;

	@ApiModelProperty(value = "期货订单状态  持仓中： Position 委托中： BuyingEntrust,PartPosition,SellingEntrust,PartUnwind 已结算： Canceled,EntrustFailure,Unwind")
	private FuturesOrderState[] states;

	@ApiModelProperty(value = "是否测试订单")
	private Boolean isTest;

	public FuturesOrderQuery() {
		super();
	}

	public FuturesOrderQuery(int page, int size) {
		super();
		super.setPage(page);
		super.setSize(size);
	}

	public FuturesOrderState[] getStates() {
		return states;
	}

	public void setStates(FuturesOrderState[] states) {
		this.states = states;
	}

	public Long getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
	}

	public Boolean getIsTest() {
		return isTest;
	}

	public void setIsTest(Boolean isTest) {
		this.isTest = isTest;
	}

}
