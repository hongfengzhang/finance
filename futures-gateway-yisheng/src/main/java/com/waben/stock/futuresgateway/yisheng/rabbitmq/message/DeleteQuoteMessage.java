package com.waben.stock.futuresgateway.yisheng.rabbitmq.message;

public class DeleteQuoteMessage {

	/** 行情ID */
	private Long quoteId;
	/**
	 * 类型
	 * <ul>
	 * <li>1表示删除FuturesContractQuote</li>
	 * <li>2表示删除FuturesQuoteMinuteK</li>
	 * </ul>
	 */
	private Integer type;

	public Long getQuoteId() {
		return quoteId;
	}

	public void setQuoteId(Long quoteId) {
		this.quoteId = quoteId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
