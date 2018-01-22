package com.waben.stock.interfaces.dto.message;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.waben.stock.interfaces.enums.MessageType;

/**
 * 
 * @author Created by hujian on 2018年1月4日
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessagingDto {

	private Long id;
	
	private String title;
	
	private String content;
	
	private MessageType type;
	
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

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getMessageType(){
		return type.getType();
	}
	
	
	
	
}
