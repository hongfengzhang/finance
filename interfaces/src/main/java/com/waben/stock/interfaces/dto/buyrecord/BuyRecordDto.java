package com.waben.stock.interfaces.dto.buyrecord;

import java.math.BigDecimal;
import java.util.Date;

import com.waben.stock.interfaces.enums.BuyRecordStatus;
import com.waben.stock.interfaces.enums.InPositionTimeType;

/**
 * 点买记录
 * 
 * @author luomengan
 *
 */
public class BuyRecordDto {

	private Long id;
	/**
	 * 系列号
	 */
	private String serialCode;
	/**
	 * 申请资金
	 */
	private BigDecimal applyAmount;
	/**
	 * 信息服务费
	 */
	private BigDecimal serviceFee;
	/**
	 * 持仓时间类型
	 */
	private InPositionTimeType positionType;
	/**
	 * 冻结资金
	 */
	private BigDecimal frozenCapital;
	/**
	 * 递延费
	 */
	private BigDecimal deferredCharges;
	/**
	 * 止盈点
	 */
	private BigDecimal stopProfitPoint;
	/**
	 * 止盈预警点位
	 */
	private BigDecimal stopProfitWarnPosition;
	/**
	 * 止盈点位
	 */
	private BigDecimal stopProfitPosition;
	/**
	 * 止损点
	 */
	private BigDecimal stopLossPoint;
	/**
	 * 止损预警点位
	 */
	private BigDecimal stopLossWarnPosition;
	/**
	 * 止损点位
	 */
	private BigDecimal stopLossPosition;
	/**
	 * 状态
	 */
	private BuyRecordStatus status;
	/**
	 * 持股数
	 */
	private Integer numberOfStrand;
	/**
	 * 委托编号，证券账号购买股票后的交易编号
	 */
	private String delegateNumber;
	/**
	 * 点买记录创建时间
	 */
	private Date createTime;
	/**
	 * 点买时间
	 */
	private Date buyingTime;
	/**
	 * 买入价格
	 */
	private BigDecimal buyingPrice;
	/**
	 * 卖出时间
	 */
	private Date sellingTime;
	/**
	 * 卖出价格
	 */
	private BigDecimal sellingPrice;
	/**
	 * 盈亏
	 */
	private BigDecimal profitOrLoss;
	/**
	 * 股票代码
	 */
	private String stockCode;
	/**
	 * 发布人ID
	 */
	private Long publisherId;
	/**
	 * 发布人序列号
	 */
	private String publisherSerialCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(BigDecimal applyAmount) {
		this.applyAmount = applyAmount;
	}

	public BigDecimal getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}

	public InPositionTimeType getPositionType() {
		return positionType;
	}

	public void setPositionType(InPositionTimeType positionType) {
		this.positionType = positionType;
	}

	public BigDecimal getFrozenCapital() {
		return frozenCapital;
	}

	public void setFrozenCapital(BigDecimal frozenCapital) {
		this.frozenCapital = frozenCapital;
	}

	public BigDecimal getStopProfitPoint() {
		return stopProfitPoint;
	}

	public void setStopProfitPoint(BigDecimal stopProfitPoint) {
		this.stopProfitPoint = stopProfitPoint;
	}

	public BigDecimal getStopProfitWarnPosition() {
		return stopProfitWarnPosition;
	}

	public void setStopProfitWarnPosition(BigDecimal stopProfitWarnPosition) {
		this.stopProfitWarnPosition = stopProfitWarnPosition;
	}

	public BigDecimal getStopProfitPosition() {
		return stopProfitPosition;
	}

	public void setStopProfitPosition(BigDecimal stopProfitPosition) {
		this.stopProfitPosition = stopProfitPosition;
	}

	public BigDecimal getStopLossPoint() {
		return stopLossPoint;
	}

	public void setStopLossPoint(BigDecimal stopLossPoint) {
		this.stopLossPoint = stopLossPoint;
	}

	public BigDecimal getStopLossWarnPosition() {
		return stopLossWarnPosition;
	}

	public void setStopLossWarnPosition(BigDecimal stopLossWarnPosition) {
		this.stopLossWarnPosition = stopLossWarnPosition;
	}

	public BigDecimal getStopLossPosition() {
		return stopLossPosition;
	}

	public void setStopLossPosition(BigDecimal stopLossPosition) {
		this.stopLossPosition = stopLossPosition;
	}

	public BuyRecordStatus getStatus() {
		return status;
	}

	public void setStatus(BuyRecordStatus status) {
		this.status = status;
	}

	public Integer getNumberOfStrand() {
		return numberOfStrand;
	}

	public void setNumberOfStrand(Integer numberOfStrand) {
		this.numberOfStrand = numberOfStrand;
	}

	public String getDelegateNumber() {
		return delegateNumber;
	}

	public void setDelegateNumber(String delegateNumber) {
		this.delegateNumber = delegateNumber;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public Long getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
	}

	public String getPublisherSerialCode() {
		return publisherSerialCode;
	}

	public void setPublisherSerialCode(String publisherSerialCode) {
		this.publisherSerialCode = publisherSerialCode;
	}

	public BigDecimal getDeferredCharges() {
		return deferredCharges;
	}

	public void setDeferredCharges(BigDecimal deferredCharges) {
		this.deferredCharges = deferredCharges;
	}

	public String getSerialCode() {
		return serialCode;
	}

	public void setSerialCode(String serialCode) {
		this.serialCode = serialCode;
	}

}
