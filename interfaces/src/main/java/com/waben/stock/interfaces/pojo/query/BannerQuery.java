package com.waben.stock.interfaces.pojo.query;

import com.waben.stock.interfaces.enums.BannerForwardCategory;

public class BannerQuery extends PageAndSortQuery {

	BannerForwardCategory category;

	private String description;
	private Integer enable;

	public BannerForwardCategory getCategory() {
		return category;
	}

	public void setCategory(BannerForwardCategory category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}
}
