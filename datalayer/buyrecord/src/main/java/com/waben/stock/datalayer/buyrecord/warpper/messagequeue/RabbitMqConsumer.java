package com.waben.stock.datalayer.buyrecord.warpper.messagequeue;

import com.waben.stock.datalayer.buyrecord.entity.BuyRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.waben.stock.datalayer.buyrecord.service.BuyRecordService;
import com.waben.stock.interfaces.pojo.stock.SecuritiesStockEntrust;

/**
 * @author Created by yuyidi on 2017/11/27.
 * @desc
 */
@Component
public class RabbitMqConsumer {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private BuyRecordService buyRecordService;

	@RabbitListener(queues = { "entrustBuyIn" })
	public void entrustBuyIn(SecuritiesStockEntrust securitiesStockEntrust) {
		logger.info("券商股票委托买入成功:{}", securitiesStockEntrust.getTradeNo());
		BuyRecord result = buyRecordService.buyInto(securitiesStockEntrust.getInvestor(), securitiesStockEntrust.getBuyRecordId(),
				securitiesStockEntrust.getEntrustPrice());
		//TODO 发送短信通知用户 和发送站内消息
		// 点买记录委托成功  点买记录状态为持仓中，则将当前订单记录放入风控消息队列
	}

	@RabbitListener(queues = { "entrustSellOut" })
	public void entrustSellOut(SecuritiesStockEntrust securitiesStockEntrust) {
		logger.info("券商股票委托卖出成功:{}", securitiesStockEntrust.getTradeNo());
		buyRecordService.sellOut(securitiesStockEntrust.getInvestor(), securitiesStockEntrust.getBuyRecordId(),
				securitiesStockEntrust.getEntrustPrice());
		// 发送短信通知用户
	}
}