package com.waben.stock.interfaces.vo.message;

import java.util.Date;

import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.annotation.JsonValue;
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

	private MessageType type;

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

	public MessageType getType() {
		return type;
	}
	
	public void setType(MessageType type) {
		this.type = type;
	}
}
