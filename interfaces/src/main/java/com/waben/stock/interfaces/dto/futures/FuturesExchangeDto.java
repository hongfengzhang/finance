package com.waben.stock.interfaces.dto.futures;

public class FuturesExchangeDto {

	private Long id;
	/**
	 * 对应的网关交易所ID
	 */
	private Long gatewayId;
	/**
	 * 交易所代码
	 */
	private String code;
	/**
	 * 交易所全称
	 */
	private String name;
	/**
	 * 交易所类型
	 * <ul>
	 * <li>1外盘</li>
	 * <li>2内盘</li>
	 * </ul>
	 */
	private Integer exchangeType;
	/**
	 * 是否可用
	 */
	private Boolean enable;
}
