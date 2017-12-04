package com.waben.stock.applayer.operation.warpper.messagequeue.rabbitmq;

import com.waben.stock.applayer.operation.warpper.messagequeue.RabbitMQProducer;
import com.waben.stock.interfaces.pojo.stock.stockjy.SecuritiesStockEntrust;
import org.springframework.stereotype.Component;

/**
 * @author Created by yuyidi on 2017/12/3.
 * @desc
 */
@Component
public class EntrustProducer extends RabbitMQProducer<SecuritiesStockEntrust> {

    public void entrust(SecuritiesStockEntrust securitiesStockEntrust) {
        super.topic("entrust", "securities", securitiesStockEntrust);
    }
}
