package com.waben.stock.interfaces.pojo.query.futures;

import com.waben.stock.interfaces.pojo.query.PageAndSortQuery;

public class FuturesContractQuery extends PageAndSortQuery {

	public FuturesContractQuery() {
		super();
	}

	public FuturesContractQuery(int page, int size) {
		super();
		super.setPage(page);
		super.setSize(size);
	}
}
