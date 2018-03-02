package com.waben.stock.datalayer.promotion.pojo.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.waben.stock.interfaces.pojo.query.PageAndSortQuery;

/**
 * 机构查询条件
 * 
 * @author luomengan
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrganizationQuery extends PageAndSortQuery {

	/**
	 * 机构代码
	 */
	private String code;
	/**
	 * 机构类型ID
	 */
	private String categoryId;
	/**
	 * 机构状态
	 */
	private String state;
	/**
	 * 父级机构ID
	 */
	private String parentId;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}
