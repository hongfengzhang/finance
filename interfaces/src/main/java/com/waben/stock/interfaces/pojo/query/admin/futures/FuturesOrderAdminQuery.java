package com.waben.stock.interfaces.pojo.query.admin.futures;

import com.waben.stock.interfaces.pojo.query.PageAndSortQuery;

public class FuturesOrderAdminQuery extends PageAndSortQuery {

	/**
	 * 交易ID
	 */
	private Long id;
	
	/**
	 * 发布人姓名
	 * <p>
	 * 实名认证的姓名
	 * </p>
	 */
	private String publisherName;
	
	/**
	 * 发布人手机号
	 */
	private String publisherPhone;
	
	/**
	 * 合约名称
	 */
	private String name;
	
	/**
	 * 交易方向
	 */
	private String orderType;
	
	/**
	 * 交易状态
	 */
	private String state;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public String getPublisherPhone() {
		return publisherPhone;
	}

	public void setPublisherPhone(String publisherPhone) {
		this.publisherPhone = publisherPhone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	
}
