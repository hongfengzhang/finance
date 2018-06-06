package com.waben.stock.interfaces.dto.futures;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import com.waben.stock.interfaces.enums.FuturesProductType;

import io.swagger.annotations.ApiModelProperty;

public class FuturesContractDto {

	private Long id;
	/**
	 * 对应的网关合约ID
	 */
	private Long gatewayId;
	/**
	 * 合约代码
	 */
	@ApiModelProperty(value = "合约代码")
	private String symbol;
	/**
	 * 合约名称
	 */
	@ApiModelProperty(value = "合约名称")
	private String name;
	/**
	 * 货币
	 */
	@ApiModelProperty(value = "货币")
	private String currency;
	/**
	 * 货币名称
	 */
	@ApiModelProperty(value = "货币名称")
	private String currencyName;
	/**
	 * 交易单位
	 */
	@ApiModelProperty(value = "交易单位")
	private String tradeUnit;
	/**
	 * 货币符号，如“$”,表示美元
	 */
	@ApiModelProperty(value = "货币符号，如“$”,表示美元")
	private String currencySign;
	/**
	 * 汇率
	 */
	@ApiModelProperty(value = "汇率")
	private BigDecimal rate;
	/**
	 * 品种分类
	 */
	@ApiModelProperty(value = "品种分类")
	private FuturesProductType productType;
	/**
	 * 乘数（1手等于多少股）
	 */
	@ApiModelProperty(value = "乘数（1手等于多少股）")
	private Integer multiplier;
	/**
	 * 最小波动
	 */
	@ApiModelProperty(value = "最小波动")
	private BigDecimal minWave;
	/**
	 * 波动一次盈亏金额，单位为该合约的货币单位
	 */
	@ApiModelProperty(value = "波动一次盈亏金额，单位为该合约的货币单位")
	private BigDecimal perWaveMoney;
	/**
	 * 一手保证金
	 */
	@ApiModelProperty(value = "一手保证金")
	private BigDecimal perUnitReserveFund;
	/**
	 * 一手强平点（亏损到剩余）
	 */
	@ApiModelProperty(value = "强平点（亏损到剩余）")
	private BigDecimal perUnitUnwindPoint;
	/**
	 * 强平点类型
	 * <ul>
	 * <li>1比例</li>
	 * <li>2金额</li>
	 * </ul>
	 */
	@ApiModelProperty(value = "强平点类型")
	private Integer unwindPointType;
	/**
	 * 用户最大持仓数
	 */
	@ApiModelProperty(value = "用户最大持仓数")
	private BigDecimal userTotalLimit;
	/**
	 * 单笔订单额度限制（手）
	 */
	@ApiModelProperty(value = "单笔订单额度限制（手）")
	private BigDecimal perOrderLimit;
	/**
	 * 开仓手续费（人民币）
	 */
	@ApiModelProperty(value = "开仓手续费（人民币）")
	private BigDecimal openwindServiceFee;
	/**
	 * 平仓手续费（人民币）
	 */
	@ApiModelProperty(value = "平仓手续费（人民币）")
	private BigDecimal unwindServiceFee;
	/**
	 * 隔夜时间
	 */
	@ApiModelProperty(value = "隔夜时间")
	private String overnightTime;
	/**
	 * 一手隔夜保证金
	 */
	@ApiModelProperty(value = "一手隔夜保证金")
	private BigDecimal overnightPerUnitReserveFund;
	/**
	 * 一手隔夜递延费
	 */
	@ApiModelProperty(value = "一手隔夜递延费")
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
	@ApiModelProperty(value = "期货合约状态,1 交易中;2 休市中;3 异常")
	private Integer state;
	/**
	 * 当天交易时间描述
	 */
	@ApiModelProperty(value = "当天交易时间描述")
	private String currentTradeTimeDesc;
	/**
	 * 交易所是否可用
	 */
	@ApiModelProperty(value = "value")
	private Boolean exchangeEnable;
	/**
	 * 北京时间的时差和交易所
	 */
	@ApiModelProperty(value = "北京时间的时差和交易所")
	private Integer timeZoneGap;
	/**
	 * 本时段持仓最后时间
	 */
	@ApiModelProperty(value = "本时段持仓最后时间")
	private String currentHoldingTime;
	/**
	 * 下一个交易时间
	 */
	@ApiModelProperty(value = "下一个交易时间")
	private String nextTradingTime;
	/**
	 * 交易所ID
	 */
	@ApiModelProperty(value = "交易所ID")
	private Long exchangeId;
	/**
	 * 交易所名称
	 */
	@ApiModelProperty(value = "交易所名称")
	private String exchangeName;
	/**
	 * 预设置的手数列表
	 */
	@ApiModelProperty(value = "预设置的手数列表")
	private Set<FuturesPreQuantityDto> preQuantitySet;

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

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getCurrentTradeTimeDesc() {
		return currentTradeTimeDesc;
	}

	public void setCurrentTradeTimeDesc(String currentTradeTimeDesc) {
		this.currentTradeTimeDesc = currentTradeTimeDesc;
	}

	public Boolean getExchangeEnable() {
		return exchangeEnable;
	}

	public void setExchangeEnable(Boolean exchangeEnable) {
		this.exchangeEnable = exchangeEnable;
	}

	public Integer getTimeZoneGap() {
		return timeZoneGap;
	}

	public void setTimeZoneGap(Integer timeZoneGap) {
		this.timeZoneGap = timeZoneGap;
	}

	public FuturesProductType getProductType() {
		return productType;
	}

	public void setProductType(FuturesProductType productType) {
		this.productType = productType;
	}

	public String getCurrentHoldingTime() {
		return currentHoldingTime;
	}

	public void setCurrentHoldingTime(String currentHoldingTime) {
		this.currentHoldingTime = currentHoldingTime;
	}

	public String getNextTradingTime() {
		return nextTradingTime;
	}

	public void setNextTradingTime(String nextTradingTime) {
		this.nextTradingTime = nextTradingTime;
	}

	public Long getExchangeId() {
		return exchangeId;
	}

	public void setExchangeId(Long exchangeId) {
		this.exchangeId = exchangeId;
	}

	public String getTradeUnit() {
		return tradeUnit;
	}

	public void setTradeUnit(String tradeUnit) {
		this.tradeUnit = tradeUnit;
	}

	public String getExchangeName() {
		return exchangeName;
	}

	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}

	public Set<FuturesPreQuantityDto> getPreQuantitySet() {
		if (preQuantitySet != null && preQuantitySet.size() > 0) {
			Object obj = preQuantitySet.iterator().next();
			if (obj instanceof FuturesPreQuantityDto) {
				TreeSet<FuturesPreQuantityDto> result = new TreeSet<>();
				result.addAll(preQuantitySet);
				Collections.checkedSortedSet(result, FuturesPreQuantityDto.class);
				return result;
			}
		}
		return preQuantitySet;
	}

	public void setPreQuantitySet(Set<FuturesPreQuantityDto> preQuantitySet) {
		this.preQuantitySet = preQuantitySet;
	}

	public String getCurrencySign() {
		return currencySign;
	}

	public void setCurrencySign(String currencySign) {
		this.currencySign = currencySign;
	}

}