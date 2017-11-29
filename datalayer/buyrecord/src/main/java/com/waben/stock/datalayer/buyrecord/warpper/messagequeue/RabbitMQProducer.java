package com.waben.stock.datalayer.buyrecord.warpper.messagequeue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Created by yuyidi on 2017/11/27.
 * @desc
 */
@Component
public class RabbitMQProducer implements Producer, RabbitTemplate.ConfirmCallback {

    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private RabbitTemplate template;

    @Override
    public void direct(String routingKey, String message) {
        template.convertAndSend(routingKey, message);
    }

    @Override
    public void topic(String exchange, String routingKey, String message) {
        template.convertAndSend(exchange, routingKey, message);
    }

    @Override
    public void fanout(String exchange, String message) {
        template.convertAndSend(exchange, "", message);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        logger.info("消息ID:{}", correlationData.getId());
    }
}
