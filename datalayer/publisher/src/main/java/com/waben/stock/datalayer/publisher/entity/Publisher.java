package com.waben.stock.datalayer.publisher.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/***
 * @author yuyidi 2017-11-15 17:30:16
 * @class com.waben.stock.datalayer.publisher.entity.Publisher
 * @description 策略发布人
 */
@Entity
@Table(name = "publisher")
public class Publisher {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 序列码
	 */
	@Column(name = "serial_code", nullable = false)
	private String serialCode;
	/**
	 * 电话
	 */
	@Column(nullable = false, unique = true)
	private String phone;
	/**
	 * 密码
	 */
	@Column
	private String password;
	/**
	 * 推广码
	 */
	@Column(unique = true)
	private String promotionCode;
	/**
	 * 推广人
	 */
	@Column
	private String promoter;
	/**
	 * 注册时间
	 */
	@Column(name = "create_time", nullable = false)
	private Date createTime;
	/**
	 * 角色
	 */
	@Column
	private Long role;
	/**
	 * 是否为测试用户，测试用户不能提现
	 */
	@Column(name = "is_test")
	private Boolean isTest;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSerialCode() {
		return serialCode;
	}

	public void setSerialCode(String serialCode) {
		this.serialCode = serialCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPromotionCode() {
		return promotionCode;
	}

	public void setPromotionCode(String promotionCode) {
		this.promotionCode = promotionCode;
	}

	public String getPromoter() {
		return promoter;
	}

	public void setPromoter(String promoter) {
		this.promoter = promoter;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getRole() {
		return role;
	}

	public void setRole(Long role) {
		this.role = role;
	}

	public Boolean getIsTest() {
		return isTest;
	}

	public void setIsTest(Boolean isTest) {
		this.isTest = isTest;
	}

}
