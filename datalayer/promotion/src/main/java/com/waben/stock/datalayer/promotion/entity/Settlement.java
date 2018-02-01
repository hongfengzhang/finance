package com.waben.stock.datalayer.promotion.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 结算
 * 
 * @author luomengan
 *
 */
@Entity
@Table(name = "p_settlement")
public class Settlement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 订单编号
	 */
	private String tradeNo;
	/**
	 * 发布人ID
	 */
	private Long publisherId;
	/**
	 * 点买记录ID
	 */
	private Long buyRecordId;
	/**
	 * 策略类型ID
	 */
	private Long strategyTypeId;
	/**
	 * 策略类型名称
	 */
	private String strategyTypeName;
	/**
	 * 信息服务费
	 */
	private BigDecimal serviceFee;
	/**
	 * 递延费
	 */
	private BigDecimal deferredFee;
	/**
	 * 软件服务费
	 */
	private BigDecimal softwareFee;
	/**
	 * 总收入
	 */
	private BigDecimal totalIncome;
	/**
	 * 平台收入
	 */
	private BigDecimal platformIncome;
	/**
	 * 运营中心收入
	 */
	private BigDecimal operationCenterIncome;
	/**
	 * 会员收入
	 */
	private BigDecimal memberIncome;
	/**
	 * 子机构收入
	 */
	private BigDecimal subOrgIncome;
	/**
	 * 经纪人收入
	 */
	private BigDecimal agentIncome;
	/**
	 * 结算时间
	 */
	private Date settlementTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public Long getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
	}

	public Long getBuyRecordId() {
		return buyRecordId;
	}

	public void setBuyRecordId(Long buyRecordId) {
		this.buyRecordId = buyRecordId;
	}

	public Long getStrategyTypeId() {
		return strategyTypeId;
	}

	public void setStrategyTypeId(Long strategyTypeId) {
		this.strategyTypeId = strategyTypeId;
	}

	public String getStrategyTypeName() {
		return strategyTypeName;
	}

	public void setStrategyTypeName(String strategyTypeName) {
		this.strategyTypeName = strategyTypeName;
	}

	public BigDecimal getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(BigDecimal totalIncome) {
		this.totalIncome = totalIncome;
	}

	public BigDecimal getPlatformIncome() {
		return platformIncome;
	}

	public void setPlatformIncome(BigDecimal platformIncome) {
		this.platformIncome = platformIncome;
	}

	public BigDecimal getOperationCenterIncome() {
		return operationCenterIncome;
	}

	public void setOperationCenterIncome(BigDecimal operationCenterIncome) {
		this.operationCenterIncome = operationCenterIncome;
	}

	public BigDecimal getMemberIncome() {
		return memberIncome;
	}

	public void setMemberIncome(BigDecimal memberIncome) {
		this.memberIncome = memberIncome;
	}

	public BigDecimal getSubOrgIncome() {
		return subOrgIncome;
	}

	public void setSubOrgIncome(BigDecimal subOrgIncome) {
		this.subOrgIncome = subOrgIncome;
	}

	public BigDecimal getAgentIncome() {
		return agentIncome;
	}

	public void setAgentIncome(BigDecimal agentIncome) {
		this.agentIncome = agentIncome;
	}

	public Date getSettlementTime() {
		return settlementTime;
	}

	public void setSettlementTime(Date settlementTime) {
		this.settlementTime = settlementTime;
	}

	public BigDecimal getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}

	public BigDecimal getDeferredFee() {
		return deferredFee;
	}

	public void setDeferredFee(BigDecimal deferredFee) {
		this.deferredFee = deferredFee;
	}

	public BigDecimal getSoftwareFee() {
		return softwareFee;
	}

	public void setSoftwareFee(BigDecimal softwareFee) {
		this.softwareFee = softwareFee;
	}

}
