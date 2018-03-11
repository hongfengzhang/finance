package com.waben.stock.datalayer.organization.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	// TODO 其他字段待确认
	/**
	 * 结算时间
	 */
	private Date settlementTime;
	/**
	 * 对应的机构
	 */
	@ManyToOne
	@JoinColumn(name = "org_id")
	private Organization org;

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

	public Date getSettlementTime() {
		return settlementTime;
	}

	public void setSettlementTime(Date settlementTime) {
		this.settlementTime = settlementTime;
	}

	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
		this.org = org;
	}

}
