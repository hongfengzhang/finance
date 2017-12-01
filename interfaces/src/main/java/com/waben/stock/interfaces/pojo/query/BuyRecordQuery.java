package com.waben.stock.interfaces.pojo.query;

import com.waben.stock.interfaces.enums.BuyRecordState;

/**
 * 点买记录 查询条件
 * 
 * @author luomengan
 *
 */
public class BuyRecordQuery extends PageAndSortQuery {

	private Long publisherId;

	private BuyRecordState[] states;

	public BuyRecordQuery() {
		super();
	}

	public BuyRecordQuery(int page, int size, Long publisherId, BuyRecordState[] states) {
		super();
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

	public BuyRecordState[] getStates() {
		return states;
	}

	public void setStates(BuyRecordState[] states) {
		this.states = states;
	}

}
