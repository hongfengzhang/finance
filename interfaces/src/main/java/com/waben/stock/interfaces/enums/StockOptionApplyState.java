package com.waben.stock.interfaces.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户申购状态
 * 
 * @author luomengan
 *
 */
public enum StockOptionApplyState implements CommonalityEnum {

	WAITCONFIRMED("1", "待确认"),

	CONFIRMED("2", "已确认"),

	FAILURE("3", "申购失败");

	private String index;

	private String state;

	private StockOptionApplyState(String index, String state) {
		this.index = index;
		this.state = state;
	}

	private static Map<String, StockOptionApplyState> indexMap = new HashMap<String, StockOptionApplyState>();

	static {
		for (StockOptionApplyState _enum : StockOptionApplyState.values()) {
			indexMap.put(_enum.getIndex(), _enum);
		}
	}

	public static StockOptionApplyState getByIndex(String index) {
		return indexMap.get(index);
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
