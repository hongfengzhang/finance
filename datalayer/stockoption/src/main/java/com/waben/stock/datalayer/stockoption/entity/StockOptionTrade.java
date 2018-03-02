package com.waben.stock.datalayer.stockoption.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.waben.stock.datalayer.stockoption.entity.enumconverter.StockOptionTradeStateConverter;
import com.waben.stock.interfaces.enums.StockOptionTradeState;

/**
 * 用户股票期权交易信息
 * 
 * @author luomengan
 *
 */
@Entity
@Table(name = "stock_option_trade")
public class StockOptionTrade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 交易单号
	 */
	private String tradeNo;
	/**
	 * 成交时间
	 */
	private Date buyingTime;
	/**
	 * 成交价格
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
	 * 盈利
	 */
	private BigDecimal profit;
	/**
	 * 到期时间
	 */
	private BigDecimal expireTime;
	/**
	 * 行权时间
	 */
	private BigDecimal rightTime;
	/**
	 * 平台是否已成交，即是否真的买入了期权
	 */
	private Boolean isPlatformTurnover;
	/**
	 * 第三方期权交易单号
	 */
	private String thirdTradeNo;
	/**
	 * 交易状态
	 */
	@Convert(converter = StockOptionTradeStateConverter.class)
	private StockOptionTradeState state;
	/**
	 * 发布人ID
	 */
	private Long publisherId;
	/**
	 * 发布人手机号码
	 */
	private Long publisherPhone;
	/**
	 * 对应的申购记录
	 */
	@OneToOne
	@JoinColumn(name = "apply_id")
	private StockOptionApply apply;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
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

	public BigDecimal getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(BigDecimal expireTime) {
		this.expireTime = expireTime;
	}

	public BigDecimal getRightTime() {
		return rightTime;
	}

	public void setRightTime(BigDecimal rightTime) {
		this.rightTime = rightTime;
	}

	public Boolean getIsPlatformTurnover() {
		return isPlatformTurnover;
	}

	public void setIsPlatformTurnover(Boolean isPlatformTurnover) {
		this.isPlatformTurnover = isPlatformTurnover;
	}

	public String getThirdTradeNo() {
		return thirdTradeNo;
	}

	public void setThirdTradeNo(String thirdTradeNo) {
		this.thirdTradeNo = thirdTradeNo;
	}

	public Long getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
	}

	public Long getPublisherPhone() {
		return publisherPhone;
	}

	public void setPublisherPhone(Long publisherPhone) {
		this.publisherPhone = publisherPhone;
	}

	public StockOptionApply getApply() {
		return apply;
	}

	public void setApply(StockOptionApply apply) {
		this.apply = apply;
	}

	public BigDecimal getProfit() {
		return profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}

	public StockOptionTradeState getState() {
		return state;
	}

	public void setState(StockOptionTradeState state) {
		this.state = state;
	}

}
