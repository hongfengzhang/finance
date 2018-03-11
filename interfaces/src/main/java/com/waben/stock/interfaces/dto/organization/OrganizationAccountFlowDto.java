package com.waben.stock.interfaces.dto.organization;

import java.math.BigDecimal;
import java.util.Date;

import com.waben.stock.interfaces.enums.OrganizationAccountFlowType;
import com.waben.stock.interfaces.enums.ResourceType;

/**
 * 机构账户流水
 * 
 * @author luomengan
 *
 */
public class OrganizationAccountFlowDto {

	private Long id;
	/**
	 * 流水号
	 */
	private String flowNo;
	/**
	 * 金额
	 */
	private BigDecimal amount;
	/**
	 * 流水类型
	 */
	private OrganizationAccountFlowType type;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 产生时间
	 */
	private Date occurrenceTime;
	/**
	 * 对应的机构
	 */
	private OrganizationDto org;
	/**
	 * 分成配置类型
	 */
	private ResourceType resourceType;
	/**
	 * 对应的资源ID
	 */
	private Long resourceId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFlowNo() {
		return flowNo;
	}

	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public OrganizationAccountFlowType getType() {
		return type;
	}

	public void setType(OrganizationAccountFlowType type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getOccurrenceTime() {
		return occurrenceTime;
	}

	public void setOccurrenceTime(Date occurrenceTime) {
		this.occurrenceTime = occurrenceTime;
	}

	public OrganizationDto getOrg() {
		return org;
	}

	public void setOrg(OrganizationDto org) {
		this.org = org;
	}

	public ResourceType getResourceType() {
		return resourceType;
	}

	public void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

}
