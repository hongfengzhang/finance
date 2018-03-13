package com.waben.stock.datalayer.organization.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

import com.waben.stock.datalayer.organization.entity.enumconverter.OrganizationAccountFlowTypeConverter;
import com.waben.stock.datalayer.organization.entity.enumconverter.ResourceTypeConverter;
import com.waben.stock.interfaces.dto.organization.OrganizationAccountDto;
import com.waben.stock.interfaces.dto.organization.OrganizationDto;
import com.waben.stock.interfaces.enums.OrganizationAccountFlowType;
import com.waben.stock.interfaces.enums.ResourceType;

/**
 * 机构账户流水
 * 
 * @author luomengan
 *
 */
@Entity
@Table(name = "p_organization_account_flow")
public class OrganizationAccountFlow {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 流水号
	 */
	@Column(name = "flow_no")
	private String flowNo;
	/**
	 * 金额
	 */
	@Column(name = "amount")
	private BigDecimal amount;
	/**
	 * 流水类型
	 */
	@Convert(converter = OrganizationAccountFlowTypeConverter.class)
	private OrganizationAccountFlowType type;
	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;
	/**
	 * 产生时间
	 */
	@Column(name = "occurrence_time")
	private Date occurrenceTime;
	/**
	 * 对应的机构
	 */
	@OneToOne
	@JoinColumn(name = "org_id")
	private Organization org;
	/**
	 * 分成配置类型
	 */
	@Convert(converter = ResourceTypeConverter.class)
	private ResourceType resourceType;
	/**
	 * 对应的资源ID
	 */
	private Long resourceId;

	@Transient
	private OrganizationDto orgDto;

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

	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
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

	public OrganizationDto getOrgDto() {
		return orgDto;
	}

	public void setOrgDto(OrganizationDto orgDto) {
		this.orgDto = orgDto;
	}
}
