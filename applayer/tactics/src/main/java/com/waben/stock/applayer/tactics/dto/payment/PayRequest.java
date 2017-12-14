package com.waben.stock.applayer.tactics.dto.payment;

import java.math.BigDecimal;

import com.waben.stock.interfaces.enums.PaymentType;

public class PayRequest {

	/**
	 * 支付方式
	 */
	private PaymentType paymentType;
	/**
	 * 支付单号
	 */
	private String paymentNo;
	/**
	 * 通知地址
	 */
	private String notifyUrl;
	/**
	 * 金额
	 */
	private BigDecimal amount;

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public String getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
