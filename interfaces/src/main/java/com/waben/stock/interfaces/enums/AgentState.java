package com.waben.stock.interfaces.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 经纪人状态
 * 
 * @author luomengan
 *
 */
public enum AgentState implements CommonalityEnum {

	TOBEAUDITED("1", "待审核"),

	AUDITEDPASSED("2", "审核通过"),

	AUDITEDREFUSED("3", "审核拒绝"),

	DISABLE("4", "禁用");

	private String index;

	private String state;

	private AgentState(String index, String state) {
		this.index = index;
		this.state = state;
	}

	private static Map<String, AgentState> indexMap = new HashMap<String, AgentState>();

	static {
		for (AgentState _enum : AgentState.values()) {
			indexMap.put(_enum.getIndex(), _enum);
		}
	}

	public static AgentState getByIndex(String index) {
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
