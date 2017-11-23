package com.waben.stock.datalayer.buyrecord.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 点买递延费
 * 
 * @author luomengan
 *
 */
@Entity
@Table(name = "deferred_charges")
public class DeferredCharges {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 费用
	 */
	@Column(name = "fee")
	private BigDecimal fee;
	/**
	 * 递延的交易日
	 */
	@Column(name = "deferred_time")
	private Date deferredTime;
	/**
	 * 扣费时间
	 */
	@Column(name = "deduction_time")
	private Date deductionTime;
	/**
	 * 点买记录ID
	 */
	@Column(name = "buy_record_id")
	private Long buyRecordId;
	/**
	 * 点买记录系列号
	 */
	@Column(name = "buy_record_serial_code")
	private String buyRecordSerialCode;
	/**
	 * 发布人ID
	 */
	@Column(name = "publisher_id")
	private Long publisherId;
	/**
	 * 发布人序列号
	 */
	@Column(name = "publisher_serial_code")
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
