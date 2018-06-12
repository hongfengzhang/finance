package com.waben.stock.futuresgateway.yisheng.rabbitmq.message;

import com.future.api.es.external.quote.bean.TapAPIQuoteContractInfo;

public class TapAPIQuoteContractInfoWithSession extends TapAPIQuoteContractInfo {

	private int sessionID;

	private String commodityNo;

	public int getSessionID() {
		return sessionID;
	}

	public void setSessionID(int sessionID) {
		this.sessionID = sessionID;
	}

	public String getCommodityNo() {
		return commodityNo;
	}

	public void setCommodityNo(String commodityNo) {
		this.commodityNo = commodityNo;
	}

}
