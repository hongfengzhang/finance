package com.waben.stock.datalayer.futures.rabbitmq.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 委托查询消息
 * 
 * @author luomengan
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EntrustQueryMessage {

	/**
	 * 订单ID
	 */
	private Long orderId;
	/**
	 * 委托类型
	 * <ul>
	 * <li>1开仓</li>
	 * <li>2平仓</li>
	 * <li>3反手</li>
	 * </ul>
	 */
	private Integer entrustType;
	/**
	 * 网关交易订单ID
	 */
	private Long gatewayOrderId;
	/**
	 * 当前消息消费次数
	 */
	private int consumeCount;

	public EntrustQueryMessage() {
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Integer getEntrustType() {
		return entrustType;
	}

	public void setEntrustType(Integer entrustType) {
		this.entrustType = entrustType;
	}

	public Long getGatewayOrderId() {
		return gatewayOrderId;
	}

	public void setGatewayOrderId(Long gatewayOrderId) {
		this.gatewayOrderId = gatewayOrderId;
	}

	public int getConsumeCount() {
		return consumeCount;
	}

	public void setConsumeCount(int consumeCount) {
		this.consumeCount = consumeCount;
	}

}
