package com.waben.stock.interfaces.dto.publisher;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.waben.stock.interfaces.enums.CapitalFlowType;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CapitalFlowDto {

	private Long id;
	/**
	 * 冻结资金
	 */
	private BigDecimal amount;
	/**
	 * 流水号
	 */
	private String flowNo;
	/**
	 * 流水类型
	 */
	private CapitalFlowType type;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 产生时间
	 */
	private Date occurrenceTime;
	/**
	 * 发布人ID
	 */
	private Long publisherId;
	/**
	 * 发布人序列号
	 */
	private String publisherSerialCode;
	/**
	 * 流水扩展列表
	 */
	private Set<CapitalFlowExtendDto> extendList;

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

	public CapitalFlowType getType() {
		return type;
	}

	public void setType(CapitalFlowType type) {
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

	public Long getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
	}

	public String getPublisherSerialCode() {
		return publisherSerialCode;
	}

	public void setPublisherSerialCode(String publisherSerialCode) {
		this.publisherSerialCode = publisherSerialCode;
	}
	
	public String getFlowNo() {
		return flowNo;
	}

	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}

	public Set<CapitalFlowExtendDto> getExtendList() {
		return extendList;
	}

	public void setExtendList(Set<CapitalFlowExtendDto> extendList) {
		this.extendList = extendList;
	}
	
	public String getCapitalFlowType(){
		String capitalFlowType = null;
		if(type != null){
			capitalFlowType = type.getType();
		}
		return capitalFlowType;
	}

}
