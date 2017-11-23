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

	Issue("1", "发布"),

	InPosition("2", "持仓中"),

	OutingPosition("3", "正在平仓"),

	OutOfPosition("4", "已平仓");

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
