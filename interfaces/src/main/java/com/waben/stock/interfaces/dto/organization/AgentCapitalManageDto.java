package com.waben.stock.interfaces.dto.organization;

import java.math.BigDecimal;
import java.util.Date;

import com.waben.stock.interfaces.enums.ResourceType;

import io.swagger.annotations.ApiModelProperty;

/**
 * 代理商资金管理Dto
 * 
 * @author sl
 *
 */
public class AgentCapitalManageDto {

	@ApiModelProperty(value = "佣金编号")
	private Long id;

	@ApiModelProperty(value = "金额")
	private BigDecimal amount;

	@ApiModelProperty(value = "流水号")
	private String flowNo;

	@ApiModelProperty(value = "流水时间")
	private Date occurrenceTime;

	@ApiModelProperty(value = "账户可用余额")
	private BigDecimal availableBalance;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "客户姓名")
	private String customerName;

	@ApiModelProperty(value = "客户账号")
	private String customerPhone;

	@ApiModelProperty(value = "合约代码")
	private String contractSymbol;

	@ApiModelProperty(value = "产品名称")
	private String contractName;

	@ApiModelProperty(value = "佣金类型")
	private ResourceType resourceType;

	@ApiModelProperty(value = "交易 佣金")
	private String commission;

	@ApiModelProperty(value = "返佣金额")
	private String amountRemaid;

	@ApiModelProperty(value = "所属代理商代码")
	private String agentCode;

	@ApiModelProperty(value = "所属代理商名称")
	private String agentName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getFlowNo() {
		return flowNo;
	}

	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}

	public Date getOccurrenceTime() {
		return occurrenceTime;
	}

	public void setOccurrenceTime(Date occurrenceTime) {
		this.occurrenceTime = occurrenceTime;
	}

	public BigDecimal getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(BigDecimal availableBalance) {
		this.availableBalance = availableBalance;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getContractSymbol() {
		return contractSymbol;
	}

	public void setContractSymbol(String contractSymbol) {
		this.contractSymbol = contractSymbol;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public ResourceType getResourceType() {
		return resourceType;
	}

	public void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
	}

	public String getCommission() {
		return commission;
	}

	public void setCommission(String commission) {
		this.commission = commission;
	}

	public String getAmountRemaid() {
		return amountRemaid;
	}

	public void setAmountRemaid(String amountRemaid) {
		this.amountRemaid = amountRemaid;
	}

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

}
