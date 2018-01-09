package com.waben.stock.interfaces.vo;

import java.util.Date;

import com.waben.stock.interfaces.enums.MessageType;

/**
 * 
 * @author Created by hujian on 2018年1月5日
 */
public class MessagingVo {

	private Long id;
	
	private String title;
	
	private String content;
	
	private String messageType;
	
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
}
