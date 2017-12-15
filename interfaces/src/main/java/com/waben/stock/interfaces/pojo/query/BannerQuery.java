package com.waben.stock.interfaces.pojo.query;

import com.waben.stock.interfaces.enums.BannerForwardCategory;

public class BannerQuery extends PageAndSortQuery {

	BannerForwardCategory category;

	public BannerForwardCategory getCategory() {
		return category;
	}

	public void setCategory(BannerForwardCategory category) {
		this.category = category;
	}

}
