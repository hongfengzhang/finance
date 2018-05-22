package com.waben.stock.futuresgateway.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 期货合约
 * 
 * @author luomengan
 *
 */
@Entity
@Table(name = "futures_contract")
public class Contract {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 合约名称
	 */
	private String symbol;
	/**
	 * 安全类型
	 */
	private String secType;
	/**
	 * 货币
	 */
	private String currency;
	/**
	 * 交易所
	 */
	private String exchange;
	/**
	 * 最高价投标合同（买方开价）
	 */
	private BigDecimal bigPrice;
	/**
	 * 以投标价格提供的合同或批次数量（买方开价）
	 */
	private int bidSize;
	/**
	 * 最低价投标合同（卖方开价）
	 */
	private BigDecimal askPrice;
	/**
	 * 以投标价格提供的合同或批次数量（卖方开价）
	 */
	private int askSize;
	/**
	 * 最新价
	 */
	private BigDecimal lastPrice;
	/**
	 * 以最新价交易的合同或批次数量
	 */
	private int lastSize;
	/**
	 * 当天最高价
	 */
	private BigDecimal highPrice;
	/**
	 * 当天最低价
	 */
	private BigDecimal lowPrice;
	/**
	 * 当天成交量
	 */
	private int volume;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getSecType() {
		return secType;
	}

	public void setSecType(String secType) {
		this.secType = secType;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public BigDecimal getBigPrice() {
		return bigPrice;
	}

	public void setBigPrice(BigDecimal bigPrice) {
		this.bigPrice = bigPrice;
	}

	public int getBidSize() {
		return bidSize;
	}

	public void setBidSize(int bidSize) {
		this.bidSize = bidSize;
	}

	public BigDecimal getAskPrice() {
		return askPrice;
	}

	public void setAskPrice(BigDecimal askPrice) {
		this.askPrice = askPrice;
	}

	public int getAskSize() {
		return askSize;
	}

	public void setAskSize(int askSize) {
		this.askSize = askSize;
	}

	public BigDecimal getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(BigDecimal lastPrice) {
		this.lastPrice = lastPrice;
	}

	public int getLastSize() {
		return lastSize;
	}

	public void setLastSize(int lastSize) {
		this.lastSize = lastSize;
	}

	public BigDecimal getHighPrice() {
		return highPrice;
	}

	public void setHighPrice(BigDecimal highPrice) {
		this.highPrice = highPrice;
	}

	public BigDecimal getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(BigDecimal lowPrice) {
		this.lowPrice = lowPrice;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

}
