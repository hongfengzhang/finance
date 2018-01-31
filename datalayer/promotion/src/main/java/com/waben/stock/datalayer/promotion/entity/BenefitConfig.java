package com.waben.stock.datalayer.promotion.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 分成配置
 * 
 * @author luomengan
 *
 */
@Entity
@Table(name = "p_benefit_config")
public class BenefitConfig {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 策略类型ID
	 */
	private Long strategyTypeId;
	/**
	 * 策略类型名称
	 */
	private String strategyTypeName;
	/**
	 * 信息服务费分成比例
	 */
	private BigDecimal serviceFeePoint;
	/**
	 * 递延费分成比例
	 */
	private BigDecimal deferredFeePoint;
	/**
	 * 软件服务费分成比例
	 */
	private BigDecimal softwareFeePoint;
	/**
	 * 是否为默认配置
	 */
	private Boolean isDefault;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public BigDecimal getServiceFeePoint() {
		return serviceFeePoint;
	}

	public void setServiceFeePoint(BigDecimal serviceFeePoint) {
		this.serviceFeePoint = serviceFeePoint;
	}

	public BigDecimal getDeferredFeePoint() {
		return deferredFeePoint;
	}

	public void setDeferredFeePoint(BigDecimal deferredFeePoint) {
		this.deferredFeePoint = deferredFeePoint;
	}

	public BigDecimal getSoftwareFeePoint() {
		return softwareFeePoint;
	}

	public void setSoftwareFeePoint(BigDecimal softwareFeePoint) {
		this.softwareFeePoint = softwareFeePoint;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

}
