package com.waben.stock.interfaces.dto.manage;

import com.waben.stock.interfaces.pojo.Response;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

/**
 * 轮播 Dto
 * 
 * @author luomengan
 *
 */
public class BannerDto {

	private Long id;
	private String link;
	private String description;
	private Integer sort;
	private Boolean enable;
	private Date createTime;
	private String bannerForward;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getBannerForward() {
		return bannerForward;
	}

	public void setBannerForward(String bannerForward) {
		this.bannerForward = bannerForward;
	}
}
