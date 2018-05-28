package com.waben.stock.interfaces.util;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class RequestParamBuilder {

	public static String build(TreeMap<String, Object> paramMap) {
		StringBuilder builder = new StringBuilder();
		for (Entry<String, Object> entry : paramMap.entrySet()) {
			builder.append(entry.getKey() + "=" + String.valueOf(entry.getValue()) + "&");
		}
		builder.deleteCharAt(builder.length() - 1);
		return builder.toString();
	}
	
	public static String build(Map<String, Object> paramMap) {
		StringBuilder builder = new StringBuilder();
		for (Entry<String, Object> entry : paramMap.entrySet()) {
			builder.append(entry.getKey() + "=" + String.valueOf(entry.getValue()) + "&");
		}
		builder.deleteCharAt(builder.length() - 1);
		return builder.toString();
	}

}
