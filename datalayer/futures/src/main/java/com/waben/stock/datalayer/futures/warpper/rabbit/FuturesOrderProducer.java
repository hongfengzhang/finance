package com.waben.stock.datalayer.futures.warpper.rabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.waben.stock.interfaces.pojo.stock.FuturesOrderEntrust;

@Component
public class FuturesOrderProducer extends RabbitMQProducer<FuturesOrderEntrust> {

	Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 自动申请买入
	 * 
	 * @param securitiesStockEntrust
	 */
	public void voluntarilyEntrustApplyBuyIn(FuturesOrderEntrust futuresOrderEntrust) {
		logger.info("开始发送自动买入订单数据:{}", futuresOrderEntrust.getTradeNo());
		super.topic("futuresOrderId", "voluntarilyBuyIn", futuresOrderEntrust);
	}

	/**
	 * 自动申请买出
	 * 
	 * @param securitiesStockEntrust
	 */
	public void voluntarilyEntrustApplySellOut(FuturesOrderEntrust futuresOrderEntrust) {
		logger.info("开始发送自动卖出订单数据:{}", futuresOrderEntrust.getTradeNo());
		super.topic("futuresOrderId", "voluntarilySellOut", futuresOrderEntrust);
	}
}
