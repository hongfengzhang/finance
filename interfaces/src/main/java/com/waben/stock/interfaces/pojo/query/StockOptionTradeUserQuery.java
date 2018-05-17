package com.waben.stock.interfaces.pojo.query;

import com.waben.stock.interfaces.enums.StockOptionTradeState;

public class StockOptionTradeUserQuery extends PageAndSortQuery {

	/**
	 * 发布人ID
	 */
	private Long publisherId;
	/**
	 * 交易状态数组
	 */
	private StockOptionTradeState[] states;
	/**
	 * 查询收益了的交易
	 */
	private boolean onlyProfit;

	public StockOptionTradeUserQuery() {

	}

	public StockOptionTradeUserQuery(int page, int size, Long publisherId, StockOptionTradeState[] states) {
		super.setPage(page);
		super.setSize(size);
		this.publisherId = publisherId;
		this.states = states;
	}

	public Long getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
	}

	public StockOptionTradeState[] getStates() {
		return states;
	}

	public void setStates(StockOptionTradeState[] states) {
		this.states = states;
	}

	public boolean isOnlyProfit() {
		return onlyProfit;
	}

	public void setOnlyProfit(boolean onlyProfit) {
		this.onlyProfit = onlyProfit;
	}

}
