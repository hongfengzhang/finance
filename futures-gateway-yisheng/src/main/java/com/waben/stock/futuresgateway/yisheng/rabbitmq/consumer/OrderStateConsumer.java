package com.waben.stock.futuresgateway.yisheng.rabbitmq.consumer;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.future.api.es.external.trade.bean.TapAPIOrderInfoNotice;
import com.waben.stock.futuresgateway.yisheng.dao.FuturesOrderDao;
import com.waben.stock.futuresgateway.yisheng.entity.FuturesOrder;
import com.waben.stock.futuresgateway.yisheng.rabbitmq.RabbitmqConfiguration;
import com.waben.stock.futuresgateway.yisheng.util.JacksonUtil;

@Component
@RabbitListener(queues = { RabbitmqConfiguration.orderStateQueueName })
public class OrderStateConsumer {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private FuturesOrderDao orderDao;

	@RabbitHandler
	public void handlerMessage(String message) {
		logger.info("消费易盛OrderState通知消息:" + message);
		TapAPIOrderInfoNotice msgObj = JacksonUtil.decode(message, TapAPIOrderInfoNotice.class);
		try {
			String orderNo = msgObj.getOrderInfo().getOrderNo();
			FuturesOrder order = orderDao.retrieveByOrderNo(orderNo);
			if (order == null) {
				int orderSessionId = msgObj.getSessionID();
				order = orderDao.retrieveByOrderSessionId(orderSessionId);
				if (order != null && order.getOrderNo() != null
						&& !order.getOrderNo().trim().equals(msgObj.getOrderInfo().getOrderNo())) {
					order = null;
				}
			}
			if (order != null && order.getOrderState() != 6) {
				order.setFilled(new BigDecimal(msgObj.getOrderInfo().getOrderMatchQty()));
				order.setLastFillPrice(new BigDecimal(msgObj.getOrderInfo().getOrderMatchPrice()));
				order.setOrderState(Integer.parseInt(String.valueOf(msgObj.getOrderInfo().getOrderState())));
				order.setOrderNo(msgObj.getOrderInfo().getOrderNo());
				order.setUpdateTime(new Date());
				orderDao.updateFuturesOrder(order);
			}
		} catch (Exception ex) {
			logger.error("消费易盛OrderState通知消息异常!", ex);
		}
	}

}
