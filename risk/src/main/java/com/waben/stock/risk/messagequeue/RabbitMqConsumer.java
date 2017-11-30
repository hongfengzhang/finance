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
public class RabbitMqConsumer {

    Logger logger = LoggerFactory.getLogger(getClass());

    @RabbitListener(queues = {"shangSecurity"})
    public void shangSecurity(String message) {
        logger.info("上海证券点买交易记录接收到消息:{}", message);
    }

    @RabbitListener(queues = {"shenSecurity"})
    public void shenSecurity(String message) {
        logger.info("深证证券点买交易记录接收到消息:{}", message);
    }

    @RabbitListener(queues = {"developSecurity"})
    public void developSecurity(String message) {
        logger.info("创业板点买交易记录接收到消息:{}", message);
    }
}
