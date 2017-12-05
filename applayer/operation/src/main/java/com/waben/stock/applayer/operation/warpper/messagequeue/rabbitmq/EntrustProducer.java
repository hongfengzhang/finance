package com.waben.stock.applayer.operation.warpper.messagequeue.rabbitmq;

import com.waben.stock.applayer.operation.warpper.messagequeue.RabbitMQProducer;
import com.waben.stock.interfaces.pojo.stock.SecuritiesStockEntrust;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Created by yuyidi on 2017/12/3.
 * @desc
 */
@Component
public class EntrustProducer extends RabbitMQProducer<SecuritiesStockEntrust> {

    Logger logger = LoggerFactory.getLogger(getClass());

    public void entrust(SecuritiesStockEntrust securitiesStockEntrust) {
        logger.info("开始发送委托订单数据:{}",securitiesStockEntrust.getTradeNo());
        super.topic("buyRecord", "securities", securitiesStockEntrust);
    }
}
