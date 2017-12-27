package com.waben.stock.interfaces.dto.publisher;

import java.util.Date;

/**
 * @author Created by yuyidi on 2017/11/12.
 * @desc
 */
public class PublisherDto {

	private Long id;
	/**
	 * 序列码
	 */
	private String serialCode;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 推广码
	 */
	private String promotionCode;
	/**
	 * 推广人
	 */
	private String promoter;
	/**
	 * 注册时间
	 */
	// @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**
	 * 是否为测试用户
	 */
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

	public Boolean getIsTest() {
		return isTest;
	}

	public void setIsTest(Boolean isTest) {
		this.isTest = isTest;
	}

}
