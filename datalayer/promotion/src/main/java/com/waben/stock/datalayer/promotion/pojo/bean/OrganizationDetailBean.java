package com.waben.stock.datalayer.promotion.pojo.bean;

import com.waben.stock.datalayer.promotion.entity.Organization;

public class OrganizationDetailBean extends Organization {

	private Integer childOrgCount;

	private Integer publisherCount;

	public Integer getChildOrgCount() {
		return childOrgCount;
	}

	public void setChildOrgCount(Integer childOrgCount) {
		this.childOrgCount = childOrgCount;
	}

	public Integer getPublisherCount() {
		return publisherCount;
	}

	public void setPublisherCount(Integer publisherCount) {
		this.publisherCount = publisherCount;
	}

}
