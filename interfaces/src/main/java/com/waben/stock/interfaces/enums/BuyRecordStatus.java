package com.waben.stock.interfaces.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 点买状态
 * 
 * @author luomengan
 *
 */
public enum BuyRecordStatus implements CommonalityEnum {

	UNKONWN("0","未知状态"),

	POSTED("1", "发布"),

	BUYLOCK("2", "买入锁定"),

	HOLDPOSITION("3", "持仓中"),

	SELLLOCK("4", "卖出锁定"),

	UNWIND("5", "已平仓");

	private BuyRecordStatus(String index, String status) {
		this.index = index;
		this.status = status;
	}

	private String index;

	private String status;

	private static Map<String, BuyRecordStatus> valueMap = new HashMap<String, BuyRecordStatus>();

	static {
		for (BuyRecordStatus _enum : BuyRecordStatus.values()) {
			valueMap.put(_enum.getIndex(), _enum);
		}
	}

	public static BuyRecordStatus getByIndex(String index) {
		BuyRecordStatus result = valueMap.get(index);
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
