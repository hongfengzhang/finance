package com.waben.stock.interfaces.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 点买状态
 * 
 * @author luomengan
 *
 */
public enum BuyRecordState implements CommonalityEnum {

	POSTED("1", "发布"),

	BUYLOCK("2", "买入锁定"),

	HOLDPOSITION("3", "持仓中"),

	SELLLOCK("4", "卖出锁定"),

	UNWIND("5", "已平仓");

	private BuyRecordState(String index, String status) {
		this.index = index;
		this.status = status;
	}

	private String index;

	private String status;

	private static Map<String, BuyRecordState> valueMap = new HashMap<String, BuyRecordState>();

	static {
		for (BuyRecordState _enum : BuyRecordState.values()) {
			valueMap.put(_enum.getIndex(), _enum);
		}
	}

	public static BuyRecordState getByIndex(String index) {
		BuyRecordState result = valueMap.get(index);
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
