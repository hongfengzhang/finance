package com.waben.stock.datalayer.publisher.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.waben.stock.datalayer.publisher.entity.enumconverter.CapitalFlowExtendTypeConverter;
import com.waben.stock.interfaces.enums.CapitalFlowExtendType;

/**
 * 资金流水扩展信息
 * 
 * @author luomengan
 *
 */
@Entity
@Table(name = "capital_flow_extend")
public class CapitalFlowExtend {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 关联的资金流水
	 */
	@JsonBackReference
	@JoinColumn(name = "flow_id")
	@ManyToOne
	private CapitalFlow flow;
	/**
	 * 扩展类型
	 */
	@Convert(converter = CapitalFlowExtendTypeConverter.class)
	@Column(name = " extend_type")
	public CapitalFlowExtendType extendType;
	/**
	 * 扩展ID
	 */
	@Column(name = "extend_id")
	public Long extendId;

	public CapitalFlowExtend() {
		super();
	}

	public CapitalFlowExtend(CapitalFlow flow, CapitalFlowExtendType extendType, Long extendId) {
		super();
		this.flow = flow;
		this.extendType = extendType;
		this.extendId = extendId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CapitalFlow getFlow() {
		return flow;
	}

	public void setFlow(CapitalFlow flow) {
		this.flow = flow;
	}

	public CapitalFlowExtendType getExtendType() {
		return extendType;
	}

	public void setExtendType(CapitalFlowExtendType extendType) {
		this.extendType = extendType;
	}

	public Long getExtendId() {
		return extendId;
	}

	public void setExtendId(Long extendId) {
		this.extendId = extendId;
	}

}
