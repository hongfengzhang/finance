package com.waben.stock.interfaces.pojo.query.admin.buyrecord;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.waben.stock.interfaces.enums.BuyRecordState;
import com.waben.stock.interfaces.pojo.query.PageAndSortQuery;

public class BuyRecordAdminQuery extends PageAndSortQuery {

	/**
	 * 点买状态
	 * <p>
	 * 状态{@link BuyRecordState}，0表示全部
	 * </p>
	 */
	private Integer state;
	/**
	 * 发布人手机号
	 */
	private String publisherPhone;
	/**
	 * 申购时间-查询开始时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	/**
	 * 申购时间-查询结束时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;
	/**
	 * 是否为测试
	 */
	private Boolean isTest;

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getPublisherPhone() {
		return publisherPhone;
	}

	public void setPublisherPhone(String publisherPhone) {
		this.publisherPhone = publisherPhone;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Boolean getIsTest() {
		return isTest;
	}

	public void setIsTest(Boolean isTest) {
		this.isTest = isTest;
	}

}
