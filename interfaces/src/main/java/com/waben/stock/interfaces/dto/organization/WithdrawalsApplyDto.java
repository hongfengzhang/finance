package com.waben.stock.interfaces.dto.organization;

import java.math.BigDecimal;
import java.util.Date;

import com.waben.stock.interfaces.enums.WithdrawalsApplyState;

/**
 * 提现申请
 * 
 * @author luomengan
 *
 */
public class WithdrawalsApplyDto {

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
	 * 机构ID
	 */
	private Long orgId;
	/**
	 * 机构代码
	 */
	private String orgCode;
	/**
	 * 机构名称
	 */
	private String orgName;

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

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

}
