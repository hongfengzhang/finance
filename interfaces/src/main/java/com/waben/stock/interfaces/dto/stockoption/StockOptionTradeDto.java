package com.waben.stock.interfaces.dto.stockoption;

import java.math.BigDecimal;
import java.util.Date;
import com.waben.stock.interfaces.enums.StockOptionBuyingType;
import com.waben.stock.interfaces.enums.StockOptionTradeState;

/**
 * 用户股票期权申购信息
 * 
 * @author luomengan
 *
 */

public class StockOptionTradeDto implements Comparable<StockOptionTradeDto> {


	private Long id;
	/**
	 * 交易单号
	 */
	private String tradeNo;
	/**
	 * 交易状态
	 */
	private StockOptionTradeState state;
	/**
	 * 股票代码
	 */
	private String stockCode;
	/**
	 * 股票名称
	 */
	private String stockName;
	/**
	 * 名义本金
	 */
	private BigDecimal nominalAmount;
	/**
	 * 权利金比例
	 */
	private BigDecimal rightMoneyRatio;
	/**
	 * 权利金
	 */
	private BigDecimal rightMoney;
	/**
	 * 周期
	 */
	private Integer cycle;
	/**
	 * 到期时间
	 */
	private BigDecimal expireTime;
	/**
	 * 申购时间
	 */
	private Date applyTime;
	/**
	 * 买入方式
	 */
	private StockOptionBuyingType buyingType;
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
	 * 行权时间
	 */
	private BigDecimal rightTime;
	/**
	 * 盈利
	 */
	private BigDecimal profit;
	/**
	 * 发布人ID
	 */
	private Long publisherId;
	/**
	 * 发布人手机号码
	 */
	private String publisherPhone;
	/**
	 * 对应的线下期权交易信息
	 */
	private OfflineStockOptionTradeDto offlineTrade;

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

	public StockOptionTradeState getState() {
		return state;
	}

	public void setState(StockOptionTradeState state) {
		this.state = state;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public BigDecimal getNominalAmount() {
		return nominalAmount;
	}

	public void setNominalAmount(BigDecimal nominalAmount) {
		this.nominalAmount = nominalAmount;
	}

	public BigDecimal getRightMoneyRatio() {
		return rightMoneyRatio;
	}

	public void setRightMoneyRatio(BigDecimal rightMoneyRatio) {
		this.rightMoneyRatio = rightMoneyRatio;
	}

	public BigDecimal getRightMoney() {
		return rightMoney;
	}

	public void setRightMoney(BigDecimal rightMoney) {
		this.rightMoney = rightMoney;
	}

	public Integer getCycle() {
		return cycle;
	}

	public void setCycle(Integer cycle) {
		this.cycle = cycle;
	}

	public BigDecimal getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(BigDecimal expireTime) {
		this.expireTime = expireTime;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public StockOptionBuyingType getBuyingType() {
		return buyingType;
	}

	public void setBuyingType(StockOptionBuyingType buyingType) {
		this.buyingType = buyingType;
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

	public BigDecimal getProfit() {
		return profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}

	public BigDecimal getRightTime() {
		return rightTime;
	}

	public void setRightTime(BigDecimal rightTime) {
		this.rightTime = rightTime;
	}

	public Long getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
	}

	public String getPublisherPhone() {
		return publisherPhone;
	}

	public void setPublisherPhone(String publisherPhone) {
		this.publisherPhone = publisherPhone;
	}

	public OfflineStockOptionTradeDto getOfflineTrade() {
		return offlineTrade;
	}

	public void setOfflineTrade(OfflineStockOptionTradeDto offlineTrade) {
		this.offlineTrade = offlineTrade;
	}

	@Override
	public int compareTo(StockOptionTradeDto o) {
		return 0;
	}
}
