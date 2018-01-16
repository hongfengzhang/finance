package com.waben.stock.interfaces.pojo.query;

import com.waben.stock.interfaces.enums.PaymentState;
import com.waben.stock.interfaces.enums.PaymentType;

public class PaymentOrderQuery extends PageAndSortQuery {

	private Long publisherId;

	private PaymentState[] states;

	private PaymentType[] types;

	public PaymentOrderQuery() {
		super();
	}

	public PaymentOrderQuery(int page, int size) {
		super();
		super.setPage(page);
		super.setSize(size);
	}

	public Long getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
	}

	public PaymentState[] getStates() {
		return states;
	}

	public void setStates(PaymentState[] states) {
		this.states = states;
	}

	public PaymentType[] getTypes() {
		return types;
	}

	public void setTypes(PaymentType[] types) {
		this.types = types;
	}

}
