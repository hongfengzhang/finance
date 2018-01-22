package com.waben.stock.interfaces.dto.publisher;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Created by yuyidi on 2017/11/12.
 * @desc
 */
@JsonIgnoreProperties(ignoreUnknown = true)
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
	 * 角色
	 */
	private Long role;
	/**
	 * 是否为测试用户
	 */
	private Boolean isTest;
	/**
	 * 用户使用的终端类型，I表示IOS，A表示Android，H表示PC
	 */
	private String endType;
	/**
	 * 头像
	 */
	private String headPortrait;

	/**
	 * 发布人信息统计
	 */
	private PublisherInformationStatisticsDto publisherInformationStatisticsDto;

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

	public String getEndType() {
		return endType;
	}

	public void setEndType(String endType) {
		this.endType = endType;
	}

	public String getHeadPortrait() {
		return headPortrait;
	}

	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait;
	}

	public PublisherInformationStatisticsDto getPublisherInformationStatisticsDto() {
		return publisherInformationStatisticsDto;
	}

	public void setPublisherInformationStatisticsDto(
			PublisherInformationStatisticsDto publisherInformationStatisticsDto) {
		this.publisherInformationStatisticsDto = publisherInformationStatisticsDto;
	}

	public Long getStrategyCount() {
		if (publisherInformationStatisticsDto != null) {
			return publisherInformationStatisticsDto.getStrategyCount();
		}
		return null;
	}

}
