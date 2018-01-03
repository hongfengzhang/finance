package com.waben.stock.datalayer.message.entity.converter;

import com.waben.stock.interfaces.enums.MessageType;

import javax.persistence.AttributeConverter;

/**
 * @author Created by yuyidi on 2018/1/3.
 * @desc
 */
public class MessageTypeConverter implements AttributeConverter<MessageType,Integer> {

    @Override
    public Integer convertToDatabaseColumn(MessageType attribute) {
        return Integer.valueOf(attribute.getIndex());
    }

    @Override
    public MessageType convertToEntityAttribute(Integer dbData) {
        return MessageType.getByType(String.valueOf(dbData));
    }

}
