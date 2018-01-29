package com.waben.stock.interfaces.dto.manage;

import java.util.Date;

/**
 * 
 * app版本
 * 
 * @author luomengan
 *
 */
public class AppVersionDto {

	private Long id;
	/**
	 * 版本号
	 */
	private String version;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 是否上线
	 */
	private Boolean isOnline;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Boolean getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Boolean isOnline) {
		this.isOnline = isOnline;
	}

}
