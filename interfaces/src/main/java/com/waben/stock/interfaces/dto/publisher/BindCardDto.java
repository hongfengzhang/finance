package com.waben.stock.interfaces.dto.publisher;

import com.waben.stock.interfaces.enums.BankEnum;

public class BindCardDto {

	private Long id;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 身份证号
	 */
	private String idCard;
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 银行卡号
	 */
	private String bankCard;
	/**
	 * 银行名称
	 */
	private String bankName;
	/**
	 * 支行名称
	 */
	private String branchName;
	/**
	 * 策略发布人序列号
	 */
	private String publisherSerialCode;

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

	public String getPublisherSerialCode() {
		return publisherSerialCode;
	}

	public void setPublisherSerialCode(String publisherSerialCode) {
		this.publisherSerialCode = publisherSerialCode;
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

	/**
	 * 获取银行图标链接
	 * 
	 * @return 银行图标链接
	 */
	public String getBankIconLink() {
		BankEnum bankEnum = BankEnum.getByBank(bankName);
		if (bankEnum == null) {
			bankEnum = BankEnum.DEFAULT;
		}
		return bankEnum.getIconLink();
	}

}
