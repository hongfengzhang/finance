package com.waben.stock.interfaces.dto.futures;

import java.math.BigDecimal;
import java.util.Date;

public class FuturesOvernightRecordDto {

	private Long id;
	/**
	 * 发布人ID
	 */
	private Long publisherId;
	/**
	 * 对应的订单
	 */
	private FuturesOrderDto order;
	/**
	 * 隔夜保证金
	 */
	private BigDecimal overnightReserveFund;
	/**
	 * 隔夜递延费
	 */
	private BigDecimal overnightDeferredFee;
	/**
	 * 递延日期
	 */
	private Date deferredTime;
	/**
	 * 扣费日期
	 */
	private Date reduceTime;
}
