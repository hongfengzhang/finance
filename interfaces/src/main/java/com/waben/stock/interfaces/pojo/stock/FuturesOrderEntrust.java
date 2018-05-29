package com.waben.stock.interfaces.pojo.stock;

import java.io.Serializable;
import java.math.BigDecimal;

import com.waben.stock.interfaces.enums.FuturesOrderState;
import com.waben.stock.interfaces.enums.FuturesOrderType;

public class FuturesOrderEntrust implements Serializable {

	/**
	 * 期货订单ID
	 */
	private Long orderId;
	/**
	 * 交易订单号
	 */
	private String tradeNo;
	/**
	 * 期货合约代码
	 */
	private String contractSymbol;
	/**
	 * 合约名
	 */
	private String contractName;
	/**
	 * 止盈点位
	 */
	private BigDecimal profitPosition;
	/**
	 * 止损点位
	 */
	private BigDecimal lossPosition;

	/**
	 * 交易所名称
	 */
	private String exchange;
	/**
	 * 状态
	 */
	private FuturesOrderState state;

	/**
	 * 订单类型(买涨或买跌)
	 */
	private FuturesOrderType orderType;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getContractSymbol() {
		return contractSymbol;
	}

	public void setContractSymbol(String contractSymbol) {
		this.contractSymbol = contractSymbol;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public BigDecimal getProfitPosition() {
		return profitPosition;
	}

	public void setProfitPosition(BigDecimal profitPosition) {
		this.profitPosition = profitPosition;
	}

	public BigDecimal getLossPosition() {
		return lossPosition;
	}

	public void setLossPosition(BigDecimal lossPosition) {
		this.lossPosition = lossPosition;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public FuturesOrderState getState() {
		return state;
	}

	public void setState(FuturesOrderState state) {
		this.state = state;
	}

	public FuturesOrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(FuturesOrderType orderType) {
		this.orderType = orderType;
	}

}
