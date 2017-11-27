package com.waben.stock.interfaces.pojo.query;

import com.waben.stock.interfaces.enums.BuyRecordState;

/**
 * 点买记录 查询条件
 * 
 * @author luomengan
 *
 */
public class BuyRecordQuery extends PageAndSortQuery {

	private BuyRecordState[] states;

	public BuyRecordQuery(int page, int size, BuyRecordState[] states) {
		super();
		super.setPage(page);
		super.setSize(size);
		this.states = states;
	}

	public BuyRecordState[] getStates() {
		return states;
	}

	public void setStates(BuyRecordState[] states) {
		this.states = states;
	}

}
