package com.waben.stock.futuresgateway.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 期货订单
 * 
 * @author luomengan
 *
 */
@Entity
@Table(name = "futures_order")
public class FuturesOrder {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 合约ID
	 */
	private Long contractId;
	/**
	 * 合约名称
	 */
	private String symbol;
	/**
	 * 账户
	 */
	private String account;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 货币
	 */
	private String currency;
	/**
	 * 总量
	 */
	private BigDecimal totalQuantity;
	/**
	 * 已成交量
	 */
	private BigDecimal filled;
	/**
	 * 剩余未成交量
	 */
	private BigDecimal remaining;
	
	private BigDecimal avgFillPrice;
	
	private BigDecimal lastFillPrice;
	
	private String permId;
	
	
	
	
}
