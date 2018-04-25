package com.waben.stock.interfaces.pojo.query.admin.publisher;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.waben.stock.interfaces.pojo.query.PageAndSortQuery;

public class CapitalFlowAdminQuery extends PageAndSortQuery {

	/**
	 * 客户手机号
	 */
	private String pulisherPhone;
	/**
	 * 客户姓名
	 * <p>
	 * 实名认证的姓名
	 * </p>
	 */
	private String publisherName;
	/**
	 * 流水时间-查询开始时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	/**
	 * 流水时间-查询结束时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;
	/**
	 * 流水类型，多个流水类型使用,号分割
	 */
	private String flowTypes;
	/**
	 * 股票代码
	 */
	private String stockCode;
	/**
	 * 充值方式
	 */
	private Integer paymentType;

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public String getPulisherPhone() {
		return pulisherPhone;
	}

	public void setPulisherPhone(String pulisherPhone) {
		this.pulisherPhone = pulisherPhone;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getFlowTypes() {
		return flowTypes;
	}

	public void setFlowTypes(String flowTypes) {
		this.flowTypes = flowTypes;
	}

	public Integer getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}

}
