package com.waben.stock.applayer.tactics.dto.futures;

import java.math.BigDecimal;

import com.waben.stock.interfaces.enums.FuturesOrderType;
import com.waben.stock.interfaces.enums.FuturesTradePriceType;

import io.swagger.annotations.ApiModelProperty;

public class FuturesOrderBuysellDto {

	@ApiModelProperty(value = "合约ID", required = true)
	private Long contractId;

	@ApiModelProperty(value = "交易数量", required = true)
	private BigDecimal totalQuantity;

	@ApiModelProperty(value = "触发止损类型 1 价格, 2 金额")
	private Integer limitLossType;

	@ApiModelProperty(value = "止损金额")
	private BigDecimal perUnitLimitLossAmount;

	@ApiModelProperty(value = "触发止盈类型  1 价格, 2 金额")
	private Integer limitProfitType;

	@ApiModelProperty(value = "止盈金额")
	private BigDecimal perUnitLimitProfitAmount;

	@ApiModelProperty(value = "订单类型", required = true)
	private FuturesOrderType orderType;

	@ApiModelProperty(value = "支付密码")
	private String paymentPassword;

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	public BigDecimal getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(BigDecimal totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public Integer getLimitLossType() {
		return limitLossType;
	}

	public void setLimitLossType(Integer limitLossType) {
		this.limitLossType = limitLossType;
	}

	public BigDecimal getPerUnitLimitLossAmount() {
		return perUnitLimitLossAmount;
	}

	public void setPerUnitLimitLossAmount(BigDecimal perUnitLimitLossAmount) {
		this.perUnitLimitLossAmount = perUnitLimitLossAmount;
	}

	public Integer getLimitProfitType() {
		return limitProfitType;
	}

	public void setLimitProfitType(Integer limitProfitType) {
		this.limitProfitType = limitProfitType;
	}

	public BigDecimal getPerUnitLimitProfitAmount() {
		return perUnitLimitProfitAmount;
	}

	public void setPerUnitLimitProfitAmount(BigDecimal perUnitLimitProfitAmount) {
		this.perUnitLimitProfitAmount = perUnitLimitProfitAmount;
	}

	public FuturesOrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(FuturesOrderType orderType) {
		this.orderType = orderType;
	}

	public String getPaymentPassword() {
		return paymentPassword;
	}

	public void setPaymentPassword(String paymentPassword) {
		this.paymentPassword = paymentPassword;
	}

}
