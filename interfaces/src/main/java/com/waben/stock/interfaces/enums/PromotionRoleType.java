package com.waben.stock.interfaces.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 推广系统角色类型
 * 
 * @author luomengan
 *
 */
public enum PromotionRoleType implements CommonalityEnum {

	SUPERADMIN("1", "超级管理员"),

	OrgAdmin("2", "机构管理员");

	private String index;

	private String type;

	private PromotionRoleType(String index, String type) {
		this.index = index;
		this.type = type;
	}

	private static Map<String, PromotionRoleType> indexMap = new HashMap<String, PromotionRoleType>();

	static {
		for (PromotionRoleType _enum : PromotionRoleType.values()) {
			indexMap.put(_enum.getIndex(), _enum);
		}
	}

	public static PromotionRoleType getByIndex(String index) {
		return indexMap.get(index);
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
