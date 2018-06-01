package com.waben.stock.interfaces.dto.admin.futures;

import java.math.BigDecimal;
import java.util.List;

import com.waben.stock.interfaces.dto.futures.FuturesContractTermDto;

public class FuturesContractAdminDto {

	/**
	 * 合约期限ID
	 */
	private Long id;
	
	private Long exchangeId;
	
	/**
	 * 交易所代码
	 */
	private String exchangcode;
	
	/**
	 * 交易所全称
	 */
	private String exchangename;
	
	/**
	 * 交易所类型
	 * <ul>
	 * <li>1外盘</li>
	 * <li>2内盘</li>
	 * </ul>
	 */
	private Integer exchangeType;
	
	/**
	 * 产品代码
	 */
	private String symbol;
	
	/**
	 * 产品名称
	 */
	private String name;
	
	/**
	 * 货币
	 */
	private String currency;
	
	/**
	 * 图标
	 */
	private String icon;
	
	/**
	 * 汇率
	 */
	private BigDecimal rate;
	
	/**
	 * 产品类型
	 */
	private String productType;
	/**
	 * 乘数（1手等于多少股）
	 */
	private Integer multiplier;
	/**
	 * 最小波动
	 */
	private BigDecimal minWave;
	/**
	 * 波动一次盈亏金额，单位为该合约的货币单位
	 */
	private BigDecimal perWaveMoney;
	
	/**
	 * 1手合约价值
	 */
	private BigDecimal perContractValue;
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
	 * 隔夜时间
	 */
	private String overnightTime;
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
	 * 期货合约状态
	 * 
	 * <p>
	 * 1 交易中
	 * </p>
	 * <p>
	 * 2 休市中
	 * </p>
	 * <p>
	 * 3 异常
	 * </p>
	 */
//	private Integer state;

	/**
	 * 每个合约的描述
	 */
//	private String describe;

	/**
	 * 交易所是否可用
	 */
//	private Boolean changeEnable;

	/**
	 * 北京时间的时差和交易所
	 */
//	private Integer timeZoneGap;

	private List<FuturesTermAdminDto> FuturesTermAdminDto;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getExchangeId() {
		return exchangeId;
	}

	public void setExchangeId(Long exchangeId) {
		this.exchangeId = exchangeId;
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

	public Integer getExchangeType() {
		return exchangeType;
	}

	public void setExchangeType(Integer exchangeType) {
		this.exchangeType = exchangeType;
	}

	public String getExchangcode() {
		return exchangcode;
	}

	public void setExchangcode(String exchangcode) {
		this.exchangcode = exchangcode;
	}

	public String getExchangename() {
		return exchangename;
	}

	public void setExchangename(String exchangename) {
		this.exchangename = exchangename;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
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

	public Integer getUnwindPointType() {
		return unwindPointType;
	}

	public void setUnwindPointType(Integer unwindPointType) {
		this.unwindPointType = unwindPointType;
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

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

//	public Integer getState() {
//		return state;
//	}
//
//	public void setState(Integer state) {
//		this.state = state;
//	}

//	public String getDescribe() {
//		return describe;
//	}
//
//	public void setDescribe(String describe) {
//		this.describe = describe;
//	}

//	public Boolean getChangeEnable() {
//		return changeEnable;
//	}
//
//	public void setChangeEnable(Boolean changeEnable) {
//		this.changeEnable = changeEnable;
//	}
//
//	public Integer getTimeZoneGap() {
//		return timeZoneGap;
//	}
//
//	public void setTimeZoneGap(Integer timeZoneGap) {
//		this.timeZoneGap = timeZoneGap;
//	}

	public List<FuturesTermAdminDto> getFuturesTermAdminDto() {
		return FuturesTermAdminDto;
	}

	public void setFuturesTermAdminDto(List<FuturesTermAdminDto> futuresTermAdminDto) {
		FuturesTermAdminDto = futuresTermAdminDto;
	}

	

	
	
}
