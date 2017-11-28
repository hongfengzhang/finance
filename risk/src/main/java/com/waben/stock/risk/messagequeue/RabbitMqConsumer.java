package com.waben.stock.risk.messagequeue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Created by yuyidi on 2017/11/27.
 * @desc
 */
@Component
public class RabbitMqConsumer implements Consumer {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    @RabbitListener(queues = {"queue"})
    public void receiveDirect(String message) {
        logger.info("Direct消息接收:{}", message);
    }

    @Override
    @RabbitListener(queues = {"topicMessage"})
    public void receiveTopic(String message) {
        logger.info("Topic消息接收:{}", message);
    }
}
