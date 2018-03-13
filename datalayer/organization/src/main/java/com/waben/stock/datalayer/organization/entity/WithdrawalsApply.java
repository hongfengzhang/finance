package com.waben.stock.datalayer.organization.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.waben.stock.interfaces.enums.WithdrawalsApplyState;

/**
 * 提现申请
 * 
 * @author luomengan
 *
 */
@Entity
@Table(name = "p_withdrawals_apply")
public class WithdrawalsApply {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 金额
	 */
	private BigDecimal amount;
	/**
	 * 申请时间
	 */
	private Date applyTime;
	/**
	 * 状态
	 */
	private WithdrawalsApplyState state;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 
	 * 对应的机构
	 */
	@OneToOne
	@JoinColumn(name = "org_id")
	private Organization org;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public WithdrawalsApplyState getState() {
		return state;
	}

	public void setState(WithdrawalsApplyState state) {
		this.state = state;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
		this.org = org;
	}

}
