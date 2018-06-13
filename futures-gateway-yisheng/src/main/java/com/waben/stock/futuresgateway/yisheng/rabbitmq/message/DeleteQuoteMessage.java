package com.waben.stock.futuresgateway.yisheng.rabbitmq.message;

public class DeleteQuoteMessage {

	/** 行情ID */
	private Long quoteId;

	public Long getQuoteId() {
		return quoteId;
	}

	public void setQuoteId(Long quoteId) {
		this.quoteId = quoteId;
	}

}
