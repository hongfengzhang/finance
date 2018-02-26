package com.waben.stock.interfaces.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 分成配置类型
 * 
 * @author luomengan
 *
 */
public enum BenefitConfigType implements CommonalityEnum {

	ORG("1", "机构"),

	AGENT("2", "经纪人");

	private String index;

	private String type;

	private BenefitConfigType(String index, String type) {
		this.index = index;
		this.type = type;
	}

	private static Map<String, BenefitConfigType> indexMap = new HashMap<String, BenefitConfigType>();

	static {
		for (BenefitConfigType _enum : BenefitConfigType.values()) {
			indexMap.put(_enum.getIndex(), _enum);
		}
	}

	public static BenefitConfigType getByIndex(String index) {
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
