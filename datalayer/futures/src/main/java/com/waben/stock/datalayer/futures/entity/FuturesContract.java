package com.waben.stock.datalayer.futures.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 期货合约
 * 
 * @author luomengan
 *
 */
@Entity
@Table(name = "f_futures_contract")
public class FuturesContract {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 对应的网关合约ID
	 */
	private Long gatewayId;
	/**
	 * 合约代码
	 */
	private String code;
	/**
	 * 合约名称
	 */
	private String name;
	/**
	 * 货币
	 */
	private String currency;
	/**
	 * 对应的交易所
	 */
	@ManyToOne
	@JoinColumn(name = "exchange_id")
	private FuturesExchange exchange;
	/**
	 * 是否可用
	 */
	private Boolean enable;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGatewayId() {
		return gatewayId;
	}

	public void setGatewayId(Long gatewayId) {
		this.gatewayId = gatewayId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public FuturesExchange getExchange() {
		return exchange;
	}

	public void setExchange(FuturesExchange exchange) {
		this.exchange = exchange;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

}
