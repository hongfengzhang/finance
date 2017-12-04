package com.waben.stock.interfaces.pojo.query;

public class CapitalFlowQuery extends PageAndSortQuery {

	private Long publisherId;

	public CapitalFlowQuery() {
		super();
	}

	public CapitalFlowQuery(int page, int size) {
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

}
