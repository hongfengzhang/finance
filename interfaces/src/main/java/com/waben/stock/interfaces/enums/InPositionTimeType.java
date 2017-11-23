package com.waben.stock.interfaces.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 持仓时间类型
 * 
 * @author luomengan
 *
 */
public enum InPositionTimeType implements CommonalityEnum {

	T1("1", "持仓T+1"),

	T5("2", "持仓T+5");

	private InPositionTimeType(String index, String status) {
		this.index = index;
		this.status = status;
	}

	private String index;

	private String status;

	private static Map<String, InPositionTimeType> valueMap = new HashMap<String, InPositionTimeType>();

	static {
		for (InPositionTimeType _enum : InPositionTimeType.values()) {
			valueMap.put(_enum.getIndex(), _enum);
		}
	}

	public static InPositionTimeType getByIndex(String index) {
		InPositionTimeType result = valueMap.get(index);
		if (result == null) {
			throw new IllegalArgumentException("No element matches " + index);
		}
		return result;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
