package com.waben.stock.datalayer.publisher.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Created by yuyidi on 2017/11/10.
 * @desc
 */
@Entity
@Table(name = "bind_card")
public class BindCard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 姓名
	 */
	@Column(name = "name")
	private String name;
	/**
	 * 身份证号
	 */
	@Column(name = "idCard")
	private String idCard;
	/**
	 * 手机号
	 */
	@Column(name = "phone")
	private String phone;
	/**
	 * 银行卡号
	 */
	@Column(name = "bank_card")
	private String bankCard;
	/**
	 * 银行名称
	 */
	@Column(name = "bank_name")
	private String bankName;
	/**
	 * 支行名称
	 */
	@Column(name = "branch_name")
	private String branchName;
	/**
	 * 支行cnaps code
	 */
	@Column(name = "branch_code")
	private String branchCode;
	/**
	 * 策略发布人ID
	 */
	@Column(name = "publisher_id")
	private Long publisherId;
	/**
	 * 对应的支付平台编号
	 */
	@Column(name = "contract_no")
	private String contractNo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public Long getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

}
