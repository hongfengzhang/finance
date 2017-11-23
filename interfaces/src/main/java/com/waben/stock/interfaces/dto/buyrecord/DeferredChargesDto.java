package com.waben.stock.interfaces.dto.buyrecord;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 递延费
 * 
 * @author luomengan
 *
 */
public class DeferredChargesDto {

	private Long id;
	/**
	 * 费用
	 */
	private BigDecimal fee;
	/**
	 * 递延的交易日
	 */
	private Date deferredTime;
	/**
	 * 扣费时间
	 */
	private Date deductionTime;
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

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public Date getDeferredTime() {
		return deferredTime;
	}

	public void setDeferredTime(Date deferredTime) {
		this.deferredTime = deferredTime;
	}

	public Date getDeductionTime() {
		return deductionTime;
	}

	public void setDeductionTime(Date deductionTime) {
		this.deductionTime = deductionTime;
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
