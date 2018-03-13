package com.waben.stock.interfaces.pojo.query.organization;

import java.util.Date;

import com.waben.stock.interfaces.pojo.query.PageAndSortQuery;

public class OrganizationAccountFlowQuery extends PageAndSortQuery {

    private Long  resourceType;
	private String flowNo;
	private Long orgId;
	private String flowType;
	private Date startTime;
	private Date endTime;



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

	public String getFlowType() {
		return flowType;
	}

	public void setFlowType(String flowType) {
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

}
