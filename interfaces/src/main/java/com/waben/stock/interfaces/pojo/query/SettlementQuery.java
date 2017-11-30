package com.waben.stock.interfaces.pojo.query;

/**
 * 结算 查询条件
 * 
 * @author luomengan
 *
 */
public class SettlementQuery extends PageAndSortQuery {

	private Long publisherId;

	private Long buyRecordId;

	public SettlementQuery() {
		super();
	}

	public SettlementQuery(int page, int size) {
		super();
		super.setPage(page);
		super.setSize(size);
	}

	public Long getBuyRecordId() {
		return buyRecordId;
	}

	public void setBuyRecordId(Long buyRecordId) {
		this.buyRecordId = buyRecordId;
	}

	public Long getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
	}

}
