package com.waben.stock.datalayer.promotion.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 绑卡
 * 
 * @author luomengan
 *
 */
@Entity
@Table(name = "p_binding_card")
public class BindingCard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 银行卡号
	 */
	private String bankCard;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 身份证号码
	 */
	private String idCard;
	/**
	 * 手机号码
	 */
	private String phone;
	/**
	 * 银行名称
	 */
	private String bankName;
	/**
	 * 开户行省份代码
	 */
	private String provinceCode;
	/**
	 * 开户行省份
	 */
	private String province;
	/**
	 * 开户行城市代码
	 */
	private String cityCode;
	/**
	 * 开户行城市
	 */
	private String city;
	/**
	 * 支行名称
	 */
	private String branchBankName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
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

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getBranchBankName() {
		return branchBankName;
	}

	public void setBranchBankName(String branchBankName) {
		this.branchBankName = branchBankName;
	}

}
