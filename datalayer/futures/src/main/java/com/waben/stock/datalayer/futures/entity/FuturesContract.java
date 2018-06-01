package com.waben.stock.datalayer.futures.entity;

import java.math.BigDecimal;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.waben.stock.datalayer.futures.entity.enumconverter.FuturesProductTypeConverter;
import com.waben.stock.interfaces.enums.FuturesProductType;

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
	private String symbol;
	/**
	 * 合约名称
	 */
	private String name;
	/**
	 * 简述
	 */
	private String sketch;
	/**
	 * 货币
	 */
	private String currency;

	/**
	 * 品种分类
	 */
	@Convert(converter = FuturesProductTypeConverter.class)
	private FuturesProductType productType;
	/**
	 * 乘数（1手等于多少股）
	 */
	private Integer multiplier;
	/**
	 * 最小波动
	 */
	private BigDecimal minWave;
	/**
	 * 1手合约价值
	 */
	private BigDecimal perContractValue;
	/**
	 * 波动一次盈亏金额，单位为该合约的货币单位
	 */
	private BigDecimal perWaveMoney;
	/**
	 * 一手保证金
	 */
	private BigDecimal perUnitReserveFund;
	/**
	 * 一手强平点（亏损到剩余）
	 */
	private BigDecimal perUnitUnwindPoint;
	/**
	 * 强平点类型
	 * <ul>
	 * <li>1比例</li>
	 * <li>2金额</li>
	 * </ul>
	 */
	private Integer unwindPointType;
	/**
	 * 该合约总计的可使用的额度（手）
	 * 
	 * <p>
	 * 需将所有未平仓的订单量相加，买涨的为正数，买跌的为负数，相加结果取绝对值再和这个总额度比较
	 * </p>
	 */
	private BigDecimal userTotalLimit;
	/**
	 * 单笔订单额度限制（手）
	 */
	private BigDecimal perOrderLimit;
	/**
	 * 开仓手续费（人民币）
	 */
	private BigDecimal openwindServiceFee;
	/**
	 * 平仓手续费（人民币）
	 */
	private BigDecimal unwindServiceFee;
	/**
	 * 隔夜时间（交易所时间）
	 * <p>
	 * 格式如04:50:00，该时间为收取隔夜手续费和递延费的时间
	 * </p>
	 */
	private String overnightTime;
	/**
	 * 返还隔夜保证金的时间（交易所时间）
	 */
	private String returnOvernightReserveFundTime;
	/**
	 * 一手隔夜保证金
	 */
	private BigDecimal overnightPerUnitReserveFund;
	/**
	 * 一手隔夜递延费
	 */
	private BigDecimal overnightPerUnitDeferredFee;
	/**
	 * 是否可用
	 */
	private Boolean enable;
	/**
	 * 对应的交易所
	 */
	@ManyToOne
	@JoinColumn(name = "exchange_id")
	private FuturesExchange exchange;

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

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
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

	public Integer getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(Integer multiplier) {
		this.multiplier = multiplier;
	}

	public BigDecimal getMinWave() {
		return minWave;
	}

	public void setMinWave(BigDecimal minWave) {
		this.minWave = minWave;
	}

	public BigDecimal getPerWaveMoney() {
		return perWaveMoney;
	}

	public void setPerWaveMoney(BigDecimal perWaveMoney) {
		this.perWaveMoney = perWaveMoney;
	}

	public BigDecimal getPerContractValue() {
		return perContractValue;
	}

	public void setPerContractValue(BigDecimal perContractValue) {
		this.perContractValue = perContractValue;
	}

	public BigDecimal getPerUnitReserveFund() {
		return perUnitReserveFund;
	}

	public void setPerUnitReserveFund(BigDecimal perUnitReserveFund) {
		this.perUnitReserveFund = perUnitReserveFund;
	}

	public BigDecimal getPerUnitUnwindPoint() {
		return perUnitUnwindPoint;
	}

	public void setPerUnitUnwindPoint(BigDecimal perUnitUnwindPoint) {
		this.perUnitUnwindPoint = perUnitUnwindPoint;
	}

	public BigDecimal getUserTotalLimit() {
		return userTotalLimit;
	}

	public void setUserTotalLimit(BigDecimal userTotalLimit) {
		this.userTotalLimit = userTotalLimit;
	}

	public BigDecimal getPerOrderLimit() {
		return perOrderLimit;
	}

	public void setPerOrderLimit(BigDecimal perOrderLimit) {
		this.perOrderLimit = perOrderLimit;
	}

	public BigDecimal getOpenwindServiceFee() {
		return openwindServiceFee;
	}

	public void setOpenwindServiceFee(BigDecimal openwindServiceFee) {
		this.openwindServiceFee = openwindServiceFee;
	}

	public BigDecimal getUnwindServiceFee() {
		return unwindServiceFee;
	}

	public void setUnwindServiceFee(BigDecimal unwindServiceFee) {
		this.unwindServiceFee = unwindServiceFee;
	}

	public String getOvernightTime() {
		return overnightTime;
	}

	public void setOvernightTime(String overnightTime) {
		this.overnightTime = overnightTime;
	}

	public BigDecimal getOvernightPerUnitReserveFund() {
		return overnightPerUnitReserveFund;
	}

	public void setOvernightPerUnitReserveFund(BigDecimal overnightPerUnitReserveFund) {
		this.overnightPerUnitReserveFund = overnightPerUnitReserveFund;
	}

	public BigDecimal getOvernightPerUnitDeferredFee() {
		return overnightPerUnitDeferredFee;
	}

	public void setOvernightPerUnitDeferredFee(BigDecimal overnightPerUnitDeferredFee) {
		this.overnightPerUnitDeferredFee = overnightPerUnitDeferredFee;
	}

	public Integer getUnwindPointType() {
		return unwindPointType;
	}

	public void setUnwindPointType(Integer unwindPointType) {
		this.unwindPointType = unwindPointType;
	}

	public FuturesProductType getProductType() {
		return productType;
	}

	public void setProductType(FuturesProductType productType) {
		this.productType = productType;
	}

	public String getReturnOvernightReserveFundTime() {
		return returnOvernightReserveFundTime;
	}

	public void setReturnOvernightReserveFundTime(String returnOvernightReserveFundTime) {
		this.returnOvernightReserveFundTime = returnOvernightReserveFundTime;
	}

	public String getSketch() {
		return sketch;
	}

	public void setSketch(String sketch) {
		this.sketch = sketch;
	}

}
