package com.waben.stock.interfaces.pojo.query;

/**
 * 
 * @author Created by hujian on 2018年1月4日
 *
 */
public class MessagingQuery extends PageAndSortQuery{

	private String title;
	
	private String messageType;
	
	private String beginTime;
	
	private String endTime;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
