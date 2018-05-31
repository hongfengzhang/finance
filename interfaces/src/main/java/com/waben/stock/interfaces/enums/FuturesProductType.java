package com.waben.stock.interfaces.enums;

import java.util.HashMap;
import java.util.Map;

public enum FuturesProductType implements CommonalityEnum {

	Commodities("1", "大宗商品期货", 1),

	Exponential("2", "指数期货", 2),

	Foreign("3", "外汇期货", 3);

	private String index;

	private String value;

	private int sort;

	private FuturesProductType(String index, String value, int sort) {
		this.index = index;
		this.value = value;
		this.sort = sort;
	}

	private static Map<String, FuturesProductType> valueMap = new HashMap<String, FuturesProductType>();

	static {
		for (FuturesProductType _enum : FuturesProductType.values()) {
			valueMap.put(_enum.getIndex(), _enum);
		}
	}

	public static FuturesProductType getByIndex(String index) {
		FuturesProductType result = valueMap.get(index);
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

}
