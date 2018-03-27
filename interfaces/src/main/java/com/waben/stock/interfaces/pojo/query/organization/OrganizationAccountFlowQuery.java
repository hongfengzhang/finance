package com.waben.stock.interfaces.pojo.query.organization;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.waben.stock.interfaces.pojo.query.PageAndSortQuery;

public class OrganizationAccountFlowQuery extends PageAndSortQuery {

    private Long  resourceType;
	private String flowNo;
	private Long orgId;
	private Long flowType;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;
	private String orgName;
	private String orgCode;
	private List<Long> orgIds;


	public Long getResourceType() {
		return resourceType;
	}

	public void setResourceType(Long resourceType) {
		this.resourceType = resourceType;
	}

	public String getFlowNo() {
		return flowNo;
	}

	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getFlowType() {
		return flowType;
	}

	public void setFlowType(Long flowType) {
		this.flowType = flowType;
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

	public List<Long> getOrgIds() {
		return orgIds;
	}

	public void setOrgIds(List<Long> orgIds) {
		this.orgIds = orgIds;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
}
