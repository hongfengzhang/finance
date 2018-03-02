package com.waben.stock.datalayer.promotion.entity.enumconverter;

import javax.persistence.AttributeConverter;

import com.waben.stock.interfaces.enums.AgentState;

/**
 * 经纪人状态 转换器
 * 
 * @author luomengan
 *
 */
public class AgentStateConverter implements AttributeConverter<AgentState, Integer> {

	/**
	 * 将枚举类型转换成数据库字段值
	 */
	@Override
	public Integer convertToDatabaseColumn(AgentState attribute) {
		return Integer.parseInt(attribute.getIndex());
	}

	/**
	 * 将数据库字段值转换成枚举
	 */
	@Override
	public AgentState convertToEntityAttribute(Integer dbData) {
		return AgentState.getByIndex(String.valueOf(dbData));
	}
}
