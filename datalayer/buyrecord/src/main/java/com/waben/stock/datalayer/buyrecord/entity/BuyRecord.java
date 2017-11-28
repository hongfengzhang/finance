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
import com.waben.stock.interfaces.enums.BuyRecordState;

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
	 * 保证金
	 */
	@Column(name = "reserve_fund")
	private BigDecimal reserveFund;
	/**
	 * 是否递延费
	 */
	@Column(name = "deferred")
	private Boolean deferred;
	/**
	 * 止盈点
	 */
	@Column(name = "profit_point")
	private BigDecimal profitPoint;
	/**
	 * 止盈预警点位
	 */
	@Column(name = "profit_warn_position")
	private BigDecimal profitWarnPosition;
	/**
	 * 止盈点位
	 */
	@Column(name = "profit_position")
	private BigDecimal profitPosition;
	/**
	 * 止损点
	 */
	@Column(name = "loss_point")
	private BigDecimal lossPoint;
	/**
	 * 止损预警点位
	 */
	@Column(name = "loss_warn_position")
	private BigDecimal lossWarnPosition;
	/**
	 * 止损点位
	 */
	@Column(name = "loss_position")
	private BigDecimal lossPosition;
	/**
	 * 状态
	 */
	@Column(name = "state")
	@Convert(converter = BuyingRecordStatusConverter.class)
	private BuyRecordState state;
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
	 * 策略类型ID
	 */
	@Column(name = "strategy_type_id")
	private Long strategyTypeId;
	/**
	 * 投资人ID
	 */
	@Column(name = "investor_id")
	private Long investorId;
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

	public String getSerialCode() {
		return serialCode;
	}

	public void setSerialCode(String serialCode) {
		this.serialCode = serialCode;
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

	public BigDecimal getReserveFund() {
		return reserveFund;
	}

	public void setReserveFund(BigDecimal reserveFund) {
		this.reserveFund = reserveFund;
	}

	public Boolean getDeferred() {
		return deferred;
	}

	public void setDeferred(Boolean deferred) {
		this.deferred = deferred;
	}

	public BigDecimal getProfitPoint() {
		return profitPoint;
	}

	public void setProfitPoint(BigDecimal profitPoint) {
		this.profitPoint = profitPoint;
	}

	public BigDecimal getProfitWarnPosition() {
		return profitWarnPosition;
	}

	public void setProfitWarnPosition(BigDecimal profitWarnPosition) {
		this.profitWarnPosition = profitWarnPosition;
	}

	public BigDecimal getProfitPosition() {
		return profitPosition;
	}

	public void setProfitPosition(BigDecimal profitPosition) {
		this.profitPosition = profitPosition;
	}

	public BigDecimal getLossPoint() {
		return lossPoint;
	}

	public void setLossPoint(BigDecimal lossPoint) {
		this.lossPoint = lossPoint;
	}

	public BigDecimal getLossWarnPosition() {
		return lossWarnPosition;
	}

	public void setLossWarnPosition(BigDecimal lossWarnPosition) {
		this.lossWarnPosition = lossWarnPosition;
	}

	public BigDecimal getLossPosition() {
		return lossPosition;
	}

	public void setLossPosition(BigDecimal lossPosition) {
		this.lossPosition = lossPosition;
	}

	public BuyRecordState getState() {
		return state;
	}

	public void setState(BuyRecordState state) {
		this.state = state;
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

	public Long getStrategyTypeId() {
		return strategyTypeId;
	}

	public void setStrategyTypeId(Long strategyTypeId) {
		this.strategyTypeId = strategyTypeId;
	}

	public Long getInvestorId() {
		return investorId;
	}

	public void setInvestorId(Long investorId) {
		this.investorId = investorId;
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

}
