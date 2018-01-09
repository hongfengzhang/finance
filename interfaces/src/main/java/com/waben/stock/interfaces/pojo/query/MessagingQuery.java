package com.waben.stock.interfaces.pojo.query;

/**
 * 
 * @author Created by hujian on 2018年1月4日
 *
 */
public class MessagingQuery extends PageAndSortQuery{

	private String title;
	
	private String messageType;
	
	private String createTime;

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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	
}
