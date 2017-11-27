package com.waben.stock.datalayer.buyrecord.warpper.messagequene;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;

/**
 * @author Created by yuyidi on 2017/11/27.
 * @desc
 */
public class RabbitMQProducer implements Producer,RabbitTemplate.ConfirmCallback {

    @Override
    public void send() {

    }

    @Override
    public void callback() {

    }

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        callback();
    }
}
