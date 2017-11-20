package com.waben.stock.interfaces.dto.manage;

import java.util.Date;

/**
 * 轮播 Dto
 * 
 * @author luomengan
 *
 */
public class BannerDto {

	/**
	 * 主键ID
	 */
	private Long id;
	/**
	 * 轮播图链接
	 */
	private String link;
	/**
	 * 描述
	 */
	private String describtion;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 状态
	 */
	private Boolean state;
	/**
	 * 创建时间
	 */
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescribtion() {
		return describtion;
	}

	public void setDescribtion(String describtion) {
		this.describtion = describtion;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Boolean getState() {
		return state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
