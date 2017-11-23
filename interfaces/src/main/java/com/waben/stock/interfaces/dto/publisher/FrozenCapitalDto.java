package com.waben.stock.interfaces.dto.publisher;

import java.math.BigDecimal;
import java.util.Date;

import com.waben.stock.interfaces.enums.FrozenCapitalStatus;

public class FrozenCapitalDto {

	private Long id;
	/**
	 * 冻结金额
	 */
	private BigDecimal amount;
	/**
	 * 状态
	 */
	private FrozenCapitalStatus status;
	/**
	 * 冻结时间
	 */
	private Date frozenTime;
	/**
	 * 解冻时间
	 */
	private Date thawTime;
	/**
	 * 点买记录ID
	 */
	private Long buyRecordId;
	/**
	 * 点买记录系列号
	 */
	private String buyRecordSerialCode;
	/**
	 * 发布人ID
	 */
	private Long publisherId;
	/**
	 * 发布人序列号
	 */
	private String publisherSerialCode;

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

	public FrozenCapitalStatus getStatus() {
		return status;
	}

	public void setStatus(FrozenCapitalStatus status) {
		this.status = status;
	}

	public Date getFrozenTime() {
		return frozenTime;
	}

	public void setFrozenTime(Date frozenTime) {
		this.frozenTime = frozenTime;
	}

	public Date getThawTime() {
		return thawTime;
	}

	public void setThawTime(Date thawTime) {
		this.thawTime = thawTime;
	}

	public Long getBuyRecordId() {
		return buyRecordId;
	}

	public void setBuyRecordId(Long buyRecordId) {
		this.buyRecordId = buyRecordId;
	}

	public String getBuyRecordSerialCode() {
		return buyRecordSerialCode;
	}

	public void setBuyRecordSerialCode(String buyRecordSerialCode) {
		this.buyRecordSerialCode = buyRecordSerialCode;
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

}
