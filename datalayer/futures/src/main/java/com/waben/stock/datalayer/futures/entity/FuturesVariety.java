package com.waben.stock.datalayer.futures.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 期货品种实体
 * 
 * @author sl
 *
 */
@Entity
@Table(name = "f_varieties")
public class FuturesVariety {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * IB代码
	 */
	private String ibCode;

	/**
	 * 品种名称
	 */
	private String name;

	/**
	 * 货币单位
	 */
	private String currencyUnit;

	/**
	 * 最小波动
	 */
	private String minWave;

	/**
	 * 波动盈亏
	 */
	private String waveBreakEven;

	/**
	 * 买入开始时间
	 */
	private Date buyDateStart;

	/**
	 * 买入结束时间
	 */
	private Date buyDateEnd;

	/**
	 * 卖出开始时间
	 */
	private Date sellDateStart;

	/**
	 * 卖出结束时间
	 */
	private Date sellDateEnd;

	/**
	 * 清仓时间
	 */
	private Date cleanHouseDate;

	/**
	 * 交易综合费
	 */
	private String tradeFee;

	/**
	 * 止损金额
	 */
	private String stopAmount;

	/**
	 * 交易保证金
	 */
	private String tradeBond;

	/**
	 * 汇率
	 */
	private String exchangeRate;

	/**
	 * 品种代码
	 */
	private String code;

	/**
	 * 品种描述
	 */
	private String describe;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIbCode() {
		return ibCode;
	}

	public void setIbCode(String ibCode) {
		this.ibCode = ibCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCurrencyUnit() {
		return currencyUnit;
	}

	public void setCurrencyUnit(String currencyUnit) {
		this.currencyUnit = currencyUnit;
	}

	public String getMinWave() {
		return minWave;
	}

	public void setMinWave(String minWave) {
		this.minWave = minWave;
	}

	public String getWaveBreakEven() {
		return waveBreakEven;
	}

	public void setWaveBreakEven(String waveBreakEven) {
		this.waveBreakEven = waveBreakEven;
	}

	public Date getBuyDateStart() {
		return buyDateStart;
	}

	public void setBuyDateStart(Date buyDateStart) {
		this.buyDateStart = buyDateStart;
	}

	public Date getBuyDateEnd() {
		return buyDateEnd;
	}

	public void setBuyDateEnd(Date buyDateEnd) {
		this.buyDateEnd = buyDateEnd;
	}

	public Date getSellDateStart() {
		return sellDateStart;
	}

	public void setSellDateStart(Date sellDateStart) {
		this.sellDateStart = sellDateStart;
	}

	public Date getSellDateEnd() {
		return sellDateEnd;
	}

	public void setSellDateEnd(Date sellDateEnd) {
		this.sellDateEnd = sellDateEnd;
	}

	public Date getCleanHouseDate() {
		return cleanHouseDate;
	}

	public void setCleanHouseDate(Date cleanHouseDate) {
		this.cleanHouseDate = cleanHouseDate;
	}

	public String getTradeFee() {
		return tradeFee;
	}

	public void setTradeFee(String tradeFee) {
		this.tradeFee = tradeFee;
	}

	public String getStopAmount() {
		return stopAmount;
	}

	public void setStopAmount(String stopAmount) {
		this.stopAmount = stopAmount;
	}

	public String getTradeBond() {
		return tradeBond;
	}

	public void setTradeBond(String tradeBond) {
		this.tradeBond = tradeBond;
	}

	public String getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

}
