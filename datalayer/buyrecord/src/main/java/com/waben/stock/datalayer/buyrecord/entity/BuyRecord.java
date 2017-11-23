package com.waben.stock.datalayer.buyrecord.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.waben.stock.datalayer.buyrecord.entity.enumconverter.BuyingRecordStatusConverter;
import com.waben.stock.datalayer.buyrecord.entity.enumconverter.InPositionTimeTypeConverter;
import com.waben.stock.interfaces.enums.BuyRecordStatus;
import com.waben.stock.interfaces.enums.InPositionTimeType;

/**
 * 点买记录
 * 
 * @author luomengan
 *
 */
@Entity
@Table(name = "buy_record")
public class BuyRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 系列号
	 */
	@Column(name = "serial_code")
	private String serialCode;
	/**
	 * 申请资金
	 */
	@Column(name = "apply_amount")
	private BigDecimal applyAmount;
	/**
	 * 信息服务费
	 */
	@Column(name = "service_fee")
	private BigDecimal serviceFee;
	/**
	 * 持仓时间类型
	 */
	@Column(name = "position_type")
	@Convert(converter = InPositionTimeTypeConverter.class)
	private InPositionTimeType positionType;
	/**
	 * 冻结资金
	 */
	@Column(name = "frozen_capital")
	private BigDecimal frozenCapital;
	/**
	 * 递延费
	 */
	@Column(name = "deferred_charges")
	private BigDecimal deferredCharges;
	/**
	 * 止盈点
	 */
	@Column(name = "stop_profit_point")
	private BigDecimal stopProfitPoint;
	/**
	 * 止盈预警点位
	 */
	@Column(name = "stop_profit_warn_position")
	private BigDecimal stopProfitWarnPosition;
	/**
	 * 止盈点位
	 */
	@Column(name = "stop_profit_position")
	private BigDecimal stopProfitPosition;
	/**
	 * 止损点
	 */
	@Column(name = "stop_loss_point")
	private BigDecimal stopLossPoint;
	/**
	 * 止损预警点位
	 */
	@Column(name = "stop_loss_warn_position")
	private BigDecimal stopLossWarnPosition;
	/**
	 * 止损点位
	 */
	@Column(name = "stop_loss_position")
	private BigDecimal stopLossPosition;
	/**
	 * 状态
	 */
	@Column(name = "status")
	@Convert(converter = BuyingRecordStatusConverter.class)
	private BuyRecordStatus status;
	/**
	 * 持股数
	 */
	@Column(name = "number_of_strand")
	private Integer numberOfStrand;
	/**
	 * 委托编号，证券账号购买股票后的交易编号
	 */
	@Column(name = "delegate_number")
	private String delegateNumber;
	/**
	 * 点买记录创建时间
	 */
	@Column(name = "create_time")
	private Date createTime;
	/**
	 * 点买时间
	 */
	@Column(name = "buying_time")
	private Date buyingTime;
	/**
	 * 买入价格
	 */
	@Column(name = "buying_price")
	private BigDecimal buyingPrice;
	/**
	 * 卖出时间
	 */
	@Column(name = "selling_time")
	private Date sellingTime;
	/**
	 * 卖出价格
	 */
	@Column(name = "selling_price")
	private BigDecimal sellingPrice;
	/**
	 * 盈亏
	 */
	@Column(name = "profit_or_loss")
	private BigDecimal profitOrLoss;
	/**
	 * 股票代码
	 */
	@Column(name = "stock_code")
	private String stockCode;
	/**
	 * 发布人ID
	 */
	@Column(name = "publisher_id")
	private Long publisherId;
	/**
	 * 发布人序列号
	 */
	@Column(name = "publisher_serial_code")
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

	public InPositionTimeType getPositionType() {
		return positionType;
	}

	public void setPositionType(InPositionTimeType positionType) {
		this.positionType = positionType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
