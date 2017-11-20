package com.waben.stock.datalayer.publisher.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.waben.stock.interfaces.dto.CapitalAccountDto;

import net.sf.cglib.beans.BeanCopier;

/***
 * @author yuyidi 2017-11-13 23:11:02
 * @class com.waben.stock.datalayer.publisher.entity.CapitalAccount
 * @description 资金账户
 */
@Entity
@Table(name = "capital_account")
public class CapitalAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 策略发布人序列号
	 */
	@Column(name = "publisher_serial_code", nullable = false)
	private String publisherSerialCode;
	/**
	 * 账户余额
	 */
	@Column(name = "balance")
	private BigDecimal balance;
	/**
	 * 支付密码
	 */
	@Column(name = "payment_password")
	private String paymentPassword;
	/**
	 * 更新时间
	 */
	@Column(name = "update_time")
	private Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPublisherSerialCode() {
		return publisherSerialCode;
	}

	public void setPublisherSerialCode(String publisherSerialCode) {
		this.publisherSerialCode = publisherSerialCode;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getPaymentPassword() {
		return paymentPassword;
	}

	public void setPaymentPassword(String paymentPassword) {
		this.paymentPassword = paymentPassword;
	}

	public CapitalAccountDto copy() {
		CapitalAccountDto result = new CapitalAccountDto();
		BeanCopier copier = BeanCopier.create(CapitalAccount.class, CapitalAccountDto.class, false);
		copier.copy(this, result, null);
		return result;
	}

}
