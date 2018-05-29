package com.waben.stock.datalayer.futures.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.waben.stock.datalayer.futures.entity.enumconverter.FuturesOrderStateConverter;
import com.waben.stock.datalayer.futures.entity.enumconverter.FuturesOrderTypeConverter;
import com.waben.stock.datalayer.futures.entity.enumconverter.FuturesTradePriceTypeConverter;
import com.waben.stock.datalayer.futures.entity.enumconverter.FuturesWindControlTypeConverter;
import com.waben.stock.interfaces.enums.FuturesOrderState;
import com.waben.stock.interfaces.enums.FuturesOrderType;
import com.waben.stock.interfaces.enums.FuturesTradePriceType;
import com.waben.stock.interfaces.enums.FuturesWindControlType;

/**
 * 交易订单实体
 * 
 * @author sl
 *
 */
@Entity
@Table(name = "f_futures_order")
public class FuturesOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 对应的发布人Id
	 */
	private Long publisherId;
	/**
	 * 发布时间
	 */
	private Date postTime;
	/**
	 * 订单类型
	 */
	@Convert(converter = FuturesOrderTypeConverter.class)
	private FuturesOrderType orderType;
	/**
	 * 数量（手）
	 */
	private BigDecimal totalQuantity;
	/**
	 * 保证金（人民币）
	 */
	private BigDecimal reserveFund;
	/**
	 * 服务费（服务费）
	 */
	private BigDecimal serviceFee;
	/**
	 * 对应的合约
	 */
	@ManyToOne
	@JoinColumn(name = "contract_id")
	private FuturesContract contract;
	/**
	 * 对应的合约期限
	 */
	@ManyToOne
	@JoinColumn(name = "contract_term_id")
	private FuturesContractTerm contractTerm;
	/**
	 * 合约代码（取期货合约设置快照）
	 */
	private String contractSymbol;
	/**
	 * 合约名称（取期货合约设置快照）
	 */
	private String contractName;
	/**
	 * 货币（取期货合约设置快照）
	 */
	private String contractCurrency;
	/**
	 * 开仓手续费（取期货合约设置快照）
	 */
	private BigDecimal openwindServiceFee;
	/**
	 * 平仓手续费（取期货合约设置快照）
	 */
	private BigDecimal unwindServiceFee;
	/**
	 * 一手强平点（取期货合约设置快照）
	 */
	private BigDecimal perUnitUnwindPoint;
	/**
	 * 强平点类型（取期货合约设置快照）
	 * <ul>
	 * <li>1比例</li>
	 * <li>2金额</li>
	 * </ul>
	 */
	private Integer unwindPointType;
	/**
	 * 一手隔夜保证金（取期货合约设置快照）
	 */
	private BigDecimal overnightPerUnitReserveFund;
	/**
	 * 一手隔夜递延费（取期货合约设置快照）
	 */
	private BigDecimal overnightPerUnitDeferredFee;
	/**
	 * 触发止盈类型（用户设置）
	 * <ul>
	 * <li>1 价格</li>
	 * <li>2 金额</li>
	 * </ul>
	 */
	private Integer limitProfitType;
	/**
	 * 止盈金额（用户设置）
	 */
	private BigDecimal perUnitLimitProfitAmount;
	/**
	 * 止盈价格点位
	 */
	private BigDecimal limitProfitPrice;
	/**
	 * 触发止损类型（用户设置）
	 * <ul>
	 * <li>1 价格</li>
	 * <li>2 金额</li>
	 * </ul>
	 */
	private Integer limitLossType;
	/**
	 * 止损金额（用户设置）
	 */
	private BigDecimal perUnitLimitLossAmount;
	/**
	 * 止损价格点位
	 */
	private BigDecimal limitLossPrice;
	/**
	 * 订单状态
	 */
	@Convert(converter = FuturesOrderStateConverter.class)
	private FuturesOrderState state;
	/**
	 * 开仓对应的网关交易订单ID
	 */
	private Long openGatewayOrderId;
	/**
	 * 买入价格类型
	 */
	@Convert(converter = FuturesTradePriceTypeConverter.class)
	private FuturesTradePriceType buyingPriceType;
	/**
	 * 买入委托价格
	 */
	private BigDecimal buyingEntrustPrice;
	/**
	 * 买入时间
	 */
	private Date buyingTime;
	/**
	 * 买入价格
	 */
	private BigDecimal buyingPrice;
	/**
	 * 平仓对应的网关交易订单ID
	 */
	private Long closeGatewayOrderId;
	/**
	 * 卖出价格类型
	 */
	@Convert(converter = FuturesTradePriceTypeConverter.class)
	private FuturesTradePriceType sellingPriceType;
	/**
	 * 卖出委托价格
	 */
	private BigDecimal sellingEntrustPrice;
	/**
	 * 卖出价格
	 */
	private Date sellingTime;
	/**
	 * 卖出价格
	 */
	private BigDecimal sellingPrice;
	/**
	 * 风控类型
	 */
	@Convert(converter = FuturesWindControlTypeConverter.class)
	private FuturesWindControlType windControlType;
	/**
	 * 盈亏（交易所货币）
	 */
	private BigDecimal currencyProfitOrLoss;
	/**
	 * 盈亏（人民币）
	 */
	private BigDecimal profitOrLoss;
	/**
	 * 发布人盈亏（人民币）
	 */
	private BigDecimal publisherProfitOrLoss;
	/**
	 * 平台盈亏（人民币）
	 */
	private BigDecimal platformProfitOrLoss;
	/**
	 * 结算时间
	 */
	private Date settlementTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 是否为测试单
	 */
	private Boolean isTest;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

	public FuturesOrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(FuturesOrderType orderType) {
		this.orderType = orderType;
	}

	public FuturesContract getContract() {
		return contract;
	}

	public void setContract(FuturesContract contract) {
		this.contract = contract;
	}

	public BigDecimal getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(BigDecimal totalQuantity) {
		this.totalQuantity = totalQuantity;
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

	public BigDecimal getPerUnitLimitProfitAmount() {
		return perUnitLimitProfitAmount;
	}

	public void setPerUnitLimitProfitAmount(BigDecimal perUnitLimitProfitAmount) {
		this.perUnitLimitProfitAmount = perUnitLimitProfitAmount;
	}

	public BigDecimal getPerUnitLimitLossAmount() {
		return perUnitLimitLossAmount;
	}

	public void setPerUnitLimitLossAmount(BigDecimal perUnitLimitLossAmount) {
		this.perUnitLimitLossAmount = perUnitLimitLossAmount;
	}

	public FuturesOrderState getState() {
		return state;
	}

	public void setState(FuturesOrderState state) {
		this.state = state;
	}

	public Long getOpenGatewayOrderId() {
		return openGatewayOrderId;
	}

	public void setOpenGatewayOrderId(Long openGatewayOrderId) {
		this.openGatewayOrderId = openGatewayOrderId;
	}

	public Date getBuyingTime() {
		return buyingTime;
	}

	public void setBuyingTime(Date buyingTime) {
		this.buyingTime = buyingTime;
	}

	public BigDecimal getBuyingPrice() {
		return buyingPrice;
	}

	public void setBuyingPrice(BigDecimal buyingPrice) {
		this.buyingPrice = buyingPrice;
	}

	public Long getCloseGatewayOrderId() {
		return closeGatewayOrderId;
	}

	public void setCloseGatewayOrderId(Long closeGatewayOrderId) {
		this.closeGatewayOrderId = closeGatewayOrderId;
	}

	public Date getSellingTime() {
		return sellingTime;
	}

	public void setSellingTime(Date sellingTime) {
		this.sellingTime = sellingTime;
	}

	public BigDecimal getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(BigDecimal sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public BigDecimal getProfitOrLoss() {
		return profitOrLoss;
	}

	public void setProfitOrLoss(BigDecimal profitOrLoss) {
		this.profitOrLoss = profitOrLoss;
	}

	public BigDecimal getPublisherProfitOrLoss() {
		return publisherProfitOrLoss;
	}

	public void setPublisherProfitOrLoss(BigDecimal publisherProfitOrLoss) {
		this.publisherProfitOrLoss = publisherProfitOrLoss;
	}

	public BigDecimal getPlatformProfitOrLoss() {
		return platformProfitOrLoss;
	}

	public void setPlatformProfitOrLoss(BigDecimal platformProfitOrLoss) {
		this.platformProfitOrLoss = platformProfitOrLoss;
	}

	public Date getSettlementTime() {
		return settlementTime;
	}

	public void setSettlementTime(Date settlementTime) {
		this.settlementTime = settlementTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public BigDecimal getReserveFund() {
		return reserveFund;
	}

	public void setReserveFund(BigDecimal reserveFund) {
		this.reserveFund = reserveFund;
	}

	public BigDecimal getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
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

	public BigDecimal getCurrencyProfitOrLoss() {
		return currencyProfitOrLoss;
	}

	public void setCurrencyProfitOrLoss(BigDecimal currencyProfitOrLoss) {
		this.currencyProfitOrLoss = currencyProfitOrLoss;
	}

	public FuturesTradePriceType getBuyingPriceType() {
		return buyingPriceType;
	}

	public void setBuyingPriceType(FuturesTradePriceType buyingPriceType) {
		this.buyingPriceType = buyingPriceType;
	}

	public BigDecimal getBuyingEntrustPrice() {
		return buyingEntrustPrice;
	}

	public void setBuyingEntrustPrice(BigDecimal buyingEntrustPrice) {
		this.buyingEntrustPrice = buyingEntrustPrice;
	}

	public FuturesTradePriceType getSellingPriceType() {
		return sellingPriceType;
	}

	public void setSellingPriceType(FuturesTradePriceType sellingPriceType) {
		this.sellingPriceType = sellingPriceType;
	}

	public BigDecimal getSellingEntrustPrice() {
		return sellingEntrustPrice;
	}

	public void setSellingEntrustPrice(BigDecimal sellingEntrustPrice) {
		this.sellingEntrustPrice = sellingEntrustPrice;
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

	public FuturesWindControlType getWindControlType() {
		return windControlType;
	}

	public void setWindControlType(FuturesWindControlType windControlType) {
		this.windControlType = windControlType;
	}

	public String getContractSymbol() {
		return contractSymbol;
	}

	public void setContractSymbol(String contractSymbol) {
		this.contractSymbol = contractSymbol;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getContractCurrency() {
		return contractCurrency;
	}

	public void setContractCurrency(String contractCurrency) {
		this.contractCurrency = contractCurrency;
	}

	public Integer getLimitProfitType() {
		return limitProfitType;
	}

	public void setLimitProfitType(Integer limitProfitType) {
		this.limitProfitType = limitProfitType;
	}

	public Integer getLimitLossType() {
		return limitLossType;
	}

	public void setLimitLossType(Integer limitLossType) {
		this.limitLossType = limitLossType;
	}

	public Boolean getIsTest() {
		return isTest;
	}

	public void setIsTest(Boolean isTest) {
		this.isTest = isTest;
	}

	public FuturesContractTerm getContractTerm() {
		return contractTerm;
	}

	public void setContractTerm(FuturesContractTerm contractTerm) {
		this.contractTerm = contractTerm;
	}

	public BigDecimal getLimitProfitPrice() {
		return limitProfitPrice;
	}

	public void setLimitProfitPrice(BigDecimal limitProfitPrice) {
		this.limitProfitPrice = limitProfitPrice;
	}

	public BigDecimal getLimitLossPrice() {
		return limitLossPrice;
	}

	public void setLimitLossPrice(BigDecimal limitLossPrice) {
		this.limitLossPrice = limitLossPrice;
	}

}
