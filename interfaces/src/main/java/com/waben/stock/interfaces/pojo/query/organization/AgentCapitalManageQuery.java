package com.waben.stock.interfaces.pojo.query.organization;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.waben.stock.interfaces.pojo.query.PageAndSortQuery;

import io.swagger.annotations.ApiModelProperty;

public class AgentCapitalManageQuery extends PageAndSortQuery {

	/**
	 * 流水类型
	 */
	@ApiModelProperty(value = "流水类型")
	private String types;
	/**
	 * 流水号
	 */
	@ApiModelProperty(value = "流水号")
	private String flowNo;
	/**
	 * 当前登陆用户所属的代理商ID
	 */
	@ApiModelProperty(value = "当前登陆用户所属的代理商ID")
	private Long currentOrgId;
	/**
	 * 客户姓名
	 */
	@ApiModelProperty(value = "客户姓名")
	private String publisherName;
	/**
	 * 客户账号
	 */
	@ApiModelProperty(value = "客户账号")
	private String publisherPhone;
	/**
	 * 当前登录树结构代码
	 */
	@ApiModelProperty(value = "当前登录树结构代码")
	private String treeCode;
	/**
	 * 代理商代码或者名称
	 */
	@ApiModelProperty(value = "代理商代码或者名称")
	private String orgCodeOrName;
	/**
	 * 合约代码或者名称
	 */
	@ApiModelProperty(value = "合约代码或者名称")
	private String contractCodeOrName;
	/**
	 * 流水时间-查询开始时间
	 */
	@ApiModelProperty(value = "流水时间-查询开始时间")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	/**
	 * 流水时间-查询结束时间
	 */
	@ApiModelProperty(value = "流水时间-查询结束时间")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

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

	public String getTreeCode() {
		return treeCode;
	}

	public void setTreeCode(String treeCode) {
		this.treeCode = treeCode;
	}

	public String getOrgCodeOrName() {
		return orgCodeOrName;
	}

	public void setOrgCodeOrName(String orgCodeOrName) {
		this.orgCodeOrName = orgCodeOrName;
	}

	public String getContractCodeOrName() {
		return contractCodeOrName;
	}

	public void setContractCodeOrName(String contractCodeOrName) {
		this.contractCodeOrName = contractCodeOrName;
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

	public String getFlowNo() {
		return flowNo;
	}

	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}

}
