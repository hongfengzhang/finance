package com.waben.stock.datalayer.buyrecord.entity.enumconverter;

import javax.persistence.AttributeConverter;

import com.waben.stock.interfaces.enums.InPositionTimeType;

/**
 * 点买记录状态 转换器
 * 
 * @author luomengan
 *
 */
public class InPositionTimeTypeConverter implements AttributeConverter<InPositionTimeType, Integer> {

	/**
	 * 将枚举类型转换成数据库字段值
	 */
	@Override
	public Integer convertToDatabaseColumn(InPositionTimeType attribute) {
		return Integer.parseInt(attribute.getIndex());
	}

	/**
	 * 将数据库字段值转换成枚举
	 */
	@Override
	public InPositionTimeType convertToEntityAttribute(Integer dbData) {
		return InPositionTimeType.getByIndex(String.valueOf(dbData));
	}
}
