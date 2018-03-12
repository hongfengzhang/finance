package com.waben.stock.interfaces.vo.organization;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 机构账户
 * 
 * @author luomengan
 *
 */
public class OrganizationAccountVo {

	private Long id;
	/**
	 * 账户余额
	 */
	private BigDecimal balance;
	/**
	 * 账户可用余额
	 */
	private BigDecimal availableBalance;
	/**
	 * 冻结资金
	 */
	private BigDecimal frozenCapital;
	/**
	 * 支付密码
	 */
	private String paymentPassword;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 分润金额
	 */
	private BigDecimal distribution;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(BigDecimal availableBalance) {
		this.availableBalance = availableBalance;
	}

	public BigDecimal getFrozenCapital() {
		return frozenCapital;
	}

	public void setFrozenCapital(BigDecimal frozenCapital) {
		this.frozenCapital = frozenCapital;
	}

	public String getPaymentPassword() {
		return paymentPassword;
	}

	public void setPaymentPassword(String paymentPassword) {
		this.paymentPassword = paymentPassword;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public BigDecimal getDistribution() {
		return distribution;
	}

	public void setDistribution(BigDecimal distribution) {
		this.distribution = distribution;
	}
}
