package com.waben.stock.interfaces.dto.admin.futures;

import java.math.BigDecimal;
import java.util.Date;

import com.waben.stock.interfaces.enums.FuturesOrderState;
import com.waben.stock.interfaces.enums.FuturesWindControlType;

public class FuturesTradeAdminDto {

	/**
	 * 交易ID
	 */
	private Long id;
	
	/**
	 * 发布人姓名
	 * <p>
	 * 实名认证的姓名
	 * </p>
	 */
	private String publisherName;
	
	/**
	 * 发布人手机号
	 */
	private String publisherPhone;
	
	/**
	 * 合约名称
	 */
	private String name;
	
	/**
	 * 交易编号
	 */
	private String tradeNo;
	
	/**
	 * 交易方向
	 */
	private String orderType;
	
	/**
	 * 交易状态
	 */
	private FuturesOrderState state;
	
	/**
	 * 数量（手）
	 */
	private BigDecimal totalQuantity;
	
	/**
	 * 买入时间
	 */
	private Date buyingTime;
	
	/**
	 * 买入价格
	 */
	private BigDecimal buyingPrice;
	
	/**
	 * 浮动盈亏
	 */
	private BigDecimal profit;
	
	/**
	 * 开仓手续费
	 */
	private BigDecimal openwindServiceFee;
	
	/**
	 * 保证金（人民币）
	 */
	private BigDecimal reserveFund;
	
	/**
	 * 隔夜手续费
	 */
	private BigDecimal overnightServiceFee;
	
	/**
	 * 止盈金额
	 */
	private BigDecimal perUnitLimitProfitAmount;
	
	/**
	 * 止损金额
	 */
	private BigDecimal perUnitLimitLossAmount;
	
	/**
	 * 持仓天数
	 */
	private Integer positionDays;
	
	/**
	 * 持仓截止日期
	 */
	private Date positionEndTime;
	
	/**
	 * 平仓时间
	 */
	private Date sellingTime;
	
	/**
	 * 平仓价格
	 */
	private BigDecimal sellingPrice;
	
	/**
	 * 平仓盈亏
	 */
	private BigDecimal sellingProfit;
	
	/**
	 * 平仓手续费
	 */
	private BigDecimal unwindServiceFee;
	
	/**
	 * 订单状态
	 */
	private FuturesWindControlType windControlType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public String getPublisherPhone() {
		return publisherPhone;
	}

	public void setPublisherPhone(String publisherPhone) {
		this.publisherPhone = publisherPhone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public FuturesOrderState getState() {
		return state;
	}

	public void setState(FuturesOrderState state) {
		this.state = state;
	}

	public BigDecimal getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(BigDecimal totalQuantity) {
		this.totalQuantity = totalQuantity;
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

	public BigDecimal getProfit() {
		return profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}

	public BigDecimal getOpenwindServiceFee() {
		return openwindServiceFee;
	}

	public void setOpenwindServiceFee(BigDecimal openwindServiceFee) {
		this.openwindServiceFee = openwindServiceFee;
	}

	public BigDecimal getReserveFund() {
		return reserveFund;
	}

	public void setReserveFund(BigDecimal reserveFund) {
		this.reserveFund = reserveFund;
	}

	public BigDecimal getOvernightServiceFee() {
		return overnightServiceFee;
	}

	public void setOvernightServiceFee(BigDecimal overnightServiceFee) {
		this.overnightServiceFee = overnightServiceFee;
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

	public Integer getPositionDays() {
		return positionDays;
	}

	public void setPositionDays(Integer positionDays) {
		this.positionDays = positionDays;
	}

	public Date getPositionEndTime() {
		return positionEndTime;
	}

	public void setPositionEndTime(Date positionEndTime) {
		this.positionEndTime = positionEndTime;
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

	public BigDecimal getSellingProfit() {
		return sellingProfit;
	}

	public void setSellingProfit(BigDecimal sellingProfit) {
		this.sellingProfit = sellingProfit;
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
}
