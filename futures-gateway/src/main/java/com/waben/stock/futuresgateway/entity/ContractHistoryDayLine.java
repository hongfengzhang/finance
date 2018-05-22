package com.waben.stock.futuresgateway.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 期货合约历史日界线
 * 
 * @author luomengan
 *
 */
@Entity
@Table(name = "futures_contract_history_dayline")
public class ContractHistoryDayLine {

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
	 * 日期
	 */
	private String day;
	/**
	 * 开盘价
	 */
	private BigDecimal open;
	/**
	 * 最高价
	 */
	private BigDecimal high;
	/**
	 * 最低价
	 */
	private BigDecimal low;
	/**
	 * 收盘价
	 */
	private BigDecimal close;
	/**
	 * 成交量
	 */
	private int volume;
	/**
	 * 成交额?
	 */
	private int count;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public BigDecimal getOpen() {
		return open;
	}

	public void setOpen(BigDecimal open) {
		this.open = open;
	}

	public BigDecimal getHigh() {
		return high;
	}

	public void setHigh(BigDecimal high) {
		this.high = high;
	}

	public BigDecimal getLow() {
		return low;
	}

	public void setLow(BigDecimal low) {
		this.low = low;
	}

	public BigDecimal getClose() {
		return close;
	}

	public void setClose(BigDecimal close) {
		this.close = close;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
