package com.waben.stock.futuresgateway.yisheng.esapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.future.api.es.external.common.bean.TapAPICommodity;

@Component
public class EsEngine {

	@Autowired
	private EsQuoteWrapper quoteWrapper;

	@Autowired
	private EsTradeWrapper tradeWrapper;

	public int qryContract(TapAPICommodity commodity) {
		return quoteWrapper.getApi().qryContract(commodity);
	}

}
