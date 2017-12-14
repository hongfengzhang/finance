package com.waben.stock.interfaces.dto.publisher;

import java.math.BigDecimal;

import com.waben.stock.interfaces.enums.PaymentState;
import com.waben.stock.interfaces.enums.PaymentType;

public class PaymentOrderDto {

	private Long id;
	/**
	 * 支付单号
	 */
	private String paymentNo;
	/**
	 * 第三方支付单号
	 */
	private String thirdPaymentNo;
	/**
	 * 金额
	 */
	private BigDecimal amount;
	/**
	 * 支付方式
	 */
	private PaymentType type;
	/**
	 * 支付状态
	 */
	private PaymentState state;
	/**
	 * 发布人ID
	 */
	private Long publisherId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	public String getThirdPaymentNo() {
		return thirdPaymentNo;
	}

	public void setThirdPaymentNo(String thirdPaymentNo) {
		this.thirdPaymentNo = thirdPaymentNo;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public PaymentType getType() {
		return type;
	}

	public void setType(PaymentType type) {
		this.type = type;
	}

	public PaymentState getState() {
		return state;
	}

	public void setState(PaymentState state) {
		this.state = state;
	}

	public Long getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
	}

}
