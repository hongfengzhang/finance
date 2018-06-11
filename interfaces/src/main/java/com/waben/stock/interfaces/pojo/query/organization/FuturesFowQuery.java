package com.waben.stock.interfaces.pojo.query.organization;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.waben.stock.interfaces.pojo.query.PageAndSortQuery;

public class FuturesFowQuery extends PageAndSortQuery {

	/**
	 * 当前登陆用户所属的代理商ID
	 */
	private Long currentOrgId;

	/**
	 * 客户姓名
	 */
	private String publisherName;

	/**
	 * 交易账号
	 */
	private String publisherPhone;
	
	/**
	 * 交易时间-查询开始时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	
	/**
	 * 交易时间-查询结束时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;
	
	/**
	 * 业务类型
	 */
	private String orderType;
	
	private String treeCode;

	/**
	 * 股票代码
	 */
	private String contractCodeOrName;

	/**
	 * 所属代理商代码/名称
	 */
	private String orgCodeOrName;

	public Long getCurrentOrgId() {
		return currentOrgId;
	}

	public void setCurrentOrgId(Long currentOrgId) {
		this.currentOrgId = currentOrgId;
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

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getTreeCode() {
		return treeCode;
	}

	public void setTreeCode(String treeCode) {
		this.treeCode = treeCode;
	}

	public String getContractCodeOrName() {
		return contractCodeOrName;
	}

	public void setContractCodeOrName(String contractCodeOrName) {
		this.contractCodeOrName = contractCodeOrName;
	}

	public String getOrgCodeOrName() {
		return orgCodeOrName;
	}

	public void setOrgCodeOrName(String orgCodeOrName) {
		this.orgCodeOrName = orgCodeOrName;
	}
}
