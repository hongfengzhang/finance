package com.waben.stock.datalayer.buyrecord.warpper.messagequeue.rabbit;

import com.waben.stock.datalayer.buyrecord.warpper.messagequeue.RabbitMQProducer;
import com.waben.stock.interfaces.pojo.stock.SecuritiesStockEntrust;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Created by yuyidi on 2017/12/3.
 * @desc
 */
@Component
public class RiskProducer extends RabbitMQProducer<SecuritiesStockEntrust> {

    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 风控持仓中订单
     * @param securitiesStockEntrust
     */
    public void risk(SecuritiesStockEntrust securitiesStockEntrust) {
        logger.info("开始发送委托买入订单数据:{}",securitiesStockEntrust.getTradeNo());
        super.topic("buyRecordRisk", "stock", securitiesStockEntrust);
    }


}
