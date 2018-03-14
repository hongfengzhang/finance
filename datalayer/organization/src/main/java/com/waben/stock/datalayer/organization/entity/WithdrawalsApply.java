package com.waben.stock.datalayer.organization.entity;

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
import javax.persistence.Transient;

import com.waben.stock.datalayer.organization.entity.enumconverter.WithdrawalsApplyStateConverter;
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
	@Convert(converter = WithdrawalsApplyStateConverter.class)
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
	/**
	 * 机构ID
	 */
	@Transient
	private Long orgId;
	/**
	 * 机构代码
	 */
	@Transient
	private String orgCode;
	/**
	 * 机构名称
	 */
	@Transient
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

	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
		this.org = org;
	}

	public Long getOrgId() {
		if(org != null) {
			return org.getId();
		}
		return orgId;
	}

	public String getOrgCode() {
		if(org != null) {
			return org.getCode();
		}
		return orgCode;
	}

	public String getOrgName() {
		if(org != null) {
			return org.getName();
		}
		return orgName;
	}

}
