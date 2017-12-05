package com.waben.stock.risk.warpper.messagequeue.rabbitmq;

import com.waben.stock.interfaces.pojo.stock.SecuritiesStockEntrust;
import com.waben.stock.risk.warpper.messagequeue.RabbitMQProducer;
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

    /**
     * 委托买入成功
     * @param securitiesStockEntrust
     */
    public void entrustBuyIn(SecuritiesStockEntrust securitiesStockEntrust) {
        logger.info("开始发送委托买入订单数据:{}",securitiesStockEntrust.getTradeNo());
        super.topic("buyRecord", "buyIn", securitiesStockEntrust);
    }

    /**
     * 委托卖出成功
     * @param securitiesStockEntrust
     */
    public void entrustSellOut(SecuritiesStockEntrust securitiesStockEntrust) {
        logger.info("开始发送委托卖出订单数据:{}",securitiesStockEntrust.getTradeNo());
        super.topic("buyRecord", "sellOut", securitiesStockEntrust);
    }
}
