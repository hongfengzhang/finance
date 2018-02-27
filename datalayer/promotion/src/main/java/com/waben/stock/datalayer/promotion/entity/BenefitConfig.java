package com.waben.stock.datalayer.promotion.entity;

import java.math.BigDecimal;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.waben.stock.datalayer.promotion.entity.enumconverter.ResourceTypeConverter;
import com.waben.stock.interfaces.enums.ResourceType;

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
	 * 分成配置类型
	 */
	@Convert(converter = ResourceTypeConverter.class)
	private ResourceType resourceType;
	/**
	 * 对应的资源ID，机构配置则对应结构ID，经纪人配置则对应经纪人ID
	 */
	private Long resourceId;

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
