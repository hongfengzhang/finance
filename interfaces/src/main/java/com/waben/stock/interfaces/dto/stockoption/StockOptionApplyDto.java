package com.waben.stock.interfaces.dto.stockoption;

import java.math.BigDecimal;
import java.util.Date;
import com.waben.stock.interfaces.enums.StockOptionBuyingType;

/**
 * 用户股票期权申购信息
 * 
 * @author luomengan
 *
 */

public class StockOptionApplyDto  implements Comparable<StockOptionApplyDto>  {


	private Long id;
	/**
	 * 申购单号
	 */
	private String applyNo;
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
	 * 申购时间
	 */
	private Date applyDate;
	/**
	 * 买入方式
	 */
	private StockOptionBuyingType buyingType;
	/**
	 * 申购状态
	 */
//	private StockOptionApplyState state;
	/**
	 * 发布人ID
	 */
	private Long publisherId;
	/**
	 * 发布人手机号码
	 */
	private Long publisherPhone;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public StockOptionBuyingType getBuyingType() {
		return buyingType;
	}

	public void setBuyingType(StockOptionBuyingType buyingType) {
		this.buyingType = buyingType;
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

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

//	public StockOptionApplyState getState() {
//		return state;
//	}
//
//	public void setState(StockOptionApplyState state) {
//		this.state = state;
//	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	@Override
	public int compareTo(StockOptionApplyDto o) {
		return 0;
	}
}
