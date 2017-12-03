package com.waben.stock.interfaces.dto.publisher;

import com.waben.stock.interfaces.enums.CapitalFlowExtendType;

public class CapitalFlowExtendDto {

	private Long id;
	/**
	 * 扩展类型
	 */
	public CapitalFlowExtendType extendType;
	/**
	 * 扩展ID
	 */
	public Long extendId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
