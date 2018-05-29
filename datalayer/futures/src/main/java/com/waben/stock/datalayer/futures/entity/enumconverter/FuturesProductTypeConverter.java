package com.waben.stock.datalayer.futures.entity.enumconverter;

import javax.persistence.AttributeConverter;

import com.waben.stock.interfaces.enums.FuturesProductType;

public class FuturesProductTypeConverter implements AttributeConverter<FuturesProductType, Integer> {

	@Override
	public Integer convertToDatabaseColumn(FuturesProductType attribute) {
		if (attribute == null) {
			return null;
		}
		return Integer.parseInt(attribute.getIndex());
	}

	@Override
	public FuturesProductType convertToEntityAttribute(Integer dbData) {
		if (dbData == null) {
			return null;
		}
		return FuturesProductType.getByIndex(String.valueOf(dbData));
	}

}
