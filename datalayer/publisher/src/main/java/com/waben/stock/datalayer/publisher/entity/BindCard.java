package com.waben.stock.datalayer.publisher.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.waben.stock.interfaces.dto.BindCardDto;

import net.sf.cglib.beans.BeanCopier;

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
	 * 策略发布人序列号
	 */
	@Column(name = "publisher_serial_code")
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

	public BindCardDto copy() {
		BindCardDto result = new BindCardDto();
		BeanCopier copier = BeanCopier.create(BindCard.class, BindCardDto.class, false);
		copier.copy(this, result, null);
		return result;
	}

}
