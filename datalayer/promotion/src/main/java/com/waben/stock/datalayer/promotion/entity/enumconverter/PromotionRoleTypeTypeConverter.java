package com.waben.stock.datalayer.promotion.entity.enumconverter;

import javax.persistence.AttributeConverter;

import com.waben.stock.interfaces.enums.PromotionRoleType;

/**
 * 推广系统角色 类型 转换器
 * 
 * @author luomengan
 *
 */
public class PromotionRoleTypeTypeConverter implements AttributeConverter<PromotionRoleType, Integer> {

	/**
	 * 将枚举类型转换成数据库字段值
	 */
	@Override
	public Integer convertToDatabaseColumn(PromotionRoleType attribute) {
		return Integer.parseInt(attribute.getIndex());
	}

	/**
	 * 将数据库字段值转换成枚举
	 */
	@Override
	public PromotionRoleType convertToEntityAttribute(Integer dbData) {
		return PromotionRoleType.getByIndex(String.valueOf(dbData));
	}
}
