package com.waben.stock.futuresgateway.yisheng.esapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.future.api.es.external.common.bean.TapAPICommodity;
import com.future.api.es.external.common.bean.TapAPIContract;

@Component
public class EsEngine {

	@Autowired
	private EsQuoteWrapper quoteWrapper;

	@Autowired
	private EsTradeWrapper tradeWrapper;

	public int qryContract(TapAPICommodity commodity) {
		return quoteWrapper.getApi().qryContract(commodity);
	}

	public int subscribeQuote(TapAPIContract contract) {
		return quoteWrapper.getApi().subscribeQuote(contract);
	}

}
