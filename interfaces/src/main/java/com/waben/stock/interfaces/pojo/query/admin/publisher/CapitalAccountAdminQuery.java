package com.waben.stock.interfaces.pojo.query.admin.publisher;

import com.waben.stock.interfaces.pojo.query.PageAndSortQuery;

public class CapitalAccountAdminQuery extends PageAndSortQuery {

	/**
	 * 用户姓名
	 */
	private String name;
	/**
	 * 手机号码
	 */
	private String phone;
	/**
	 * 状态
	 * <ul>
	 * <li>1正常，因为该字段为新加的字段，所以数据为空的情况也是表示正常状态</li>
	 * <li>2冻结，不能提现、不能交易</li>
	 * </ul>
	 */
	private Integer state;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}