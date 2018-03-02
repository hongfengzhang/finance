package com.waben.stock.datalayer.promotion.entity;

import java.util.Date;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.waben.stock.datalayer.promotion.entity.enumconverter.AgentStateConverter;
import com.waben.stock.interfaces.enums.AgentState;

/**
 * 经纪人
 * 
 * @author luomengan
 *
 */
@Entity
@Table(name = "p_agent")
public class Agent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 对应的发布人ID
	 */
	private Long publisherId;
	/**
	 * 对应的发布人手机号码
	 */
	private String publisherPhone;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 经纪人状态
	 */
	@Convert(converter = AgentStateConverter.class)
	private AgentState state;
	/**
	 * 所属机构
	 */
	@ManyToOne
	@JoinColumn(name = "org_id")
	private Organization org;
	/**
	 * 申请时间
	 */
	private Date applyTime;
	/**
	 * 审核时间
	 */
	private Date auditTime;
	/**
	 * 审核人
	 */
	@ManyToOne
	@JoinColumn(name = "audit_user")
	private User auditUser;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AgentState getState() {
		return state;
	}

	public void setState(AgentState state) {
		this.state = state;
	}

	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
		this.org = org;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public User getAuditUser() {
		return auditUser;
	}

	public void setAuditUser(User auditUser) {
		this.auditUser = auditUser;
	}

}
