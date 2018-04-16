package com.waben.stock.interfaces.pojo.query.organization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.waben.stock.interfaces.pojo.query.PageAndSortQuery;

/**
 * 客户查询条件
 * 
 * @author luomengan
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerQuery extends PageAndSortQuery {

	/**
	 * 客户ID
	 */
	private String publisherId;
	/**
	 * 客户手机号
	 */
	private String publisherPhone;
	/**
	 * 当前机构代码
	 */
	private String currentOrgCode;
	/**
	 * 查询的机构代码
	 */
	private String orgCode;
	/**
	 * 查询的机构名称
	 */
	private String orgName;
	/**
	 * 用户类型，0表示所有，1表示正式用户，2表示测试用户
	 */
	private Integer userType;

	public String getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
	}

	public String getPublisherPhone() {
		return publisherPhone;
	}

	public void setPublisherPhone(String publisherPhone) {
		this.publisherPhone = publisherPhone;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getCurrentOrgCode() {
		return currentOrgCode;
	}

	public void setCurrentOrgCode(String currentOrgCode) {
		this.currentOrgCode = currentOrgCode;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

}
