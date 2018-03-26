package com.waben.stock.collector.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 资金流水扩展信息
 * 
 * @author luomengan
 *
 */
@Entity
@Table(name = "collector_capital_flow_extend")
public class CapitalFlowExtend {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long dataId;
	private String domain;
	/**
	 * 关联的资金流水
	 */
	private Long flowId;
	/**
	 * 扩展类型
	 */
	public Integer extendType;
	/**
	 * 扩展ID
	 */
	@Column(name = "extend_id")
	public Long extendId;

	public CapitalFlowExtend() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getExtendType() {
		return extendType;
	}

	public void setExtendType(Integer extendType) {
		this.extendType = extendType;
	}

	public Long getExtendId() {
		return extendId;
	}

	public void setExtendId(Long extendId) {
		this.extendId = extendId;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public Long getFlowId() {
		return flowId;
	}

	public void setFlowId(Long flowId) {
		this.flowId = flowId;
	}

	public Long getDataId() {
		return dataId;
	}

	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}

}
