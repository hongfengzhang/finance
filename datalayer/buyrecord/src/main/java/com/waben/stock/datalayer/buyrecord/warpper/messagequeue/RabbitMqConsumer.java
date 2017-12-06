package com.waben.stock.datalayer.buyrecord.warpper.messagequeue;

import com.waben.stock.datalayer.buyrecord.service.BuyRecordService;
import com.waben.stock.interfaces.pojo.stock.SecuritiesStockEntrust;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author Created by yuyidi on 2017/11/27.
 * @desc
 */
@Component
public class RabbitMqConsumer {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BuyRecordService buyRecordService;

    @RabbitListener(queues = {"entrustBuyIn"})
    public void entrustBuyIn(SecuritiesStockEntrust securitiesStockEntrust) {
        logger.info("券商股票委托买入成功:{}", securitiesStockEntrust.getTradeNo());
        buyRecordService.buyInto(securitiesStockEntrust.getInvestor(), securitiesStockEntrust.getBuyRecordId(),
                securitiesStockEntrust.getEntrustPrice());
        //发送短信通知用户
    }

    @RabbitListener(queues = {"entrustSellOut"})
    public void entrustSellOut(SecuritiesStockEntrust securitiesStockEntrust) {
        logger.info("券商股票委托卖出成功:{}", securitiesStockEntrust.getTradeNo());
        buyRecordService.sellOut(securitiesStockEntrust.getInvestor(), securitiesStockEntrust.getBuyRecordId(),
                securitiesStockEntrust.getEntrustPrice(), new BigDecimal(10));
        //发送短信通知用户
    }
}