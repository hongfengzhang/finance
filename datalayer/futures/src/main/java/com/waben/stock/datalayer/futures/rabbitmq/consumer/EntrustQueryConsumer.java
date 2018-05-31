package com.waben.stock.datalayer.futures.rabbitmq.consumer;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.waben.stock.datalayer.futures.rabbitmq.RabbitmqConfiguration;
import com.waben.stock.datalayer.futures.rabbitmq.RabbitmqProducer;
import com.waben.stock.datalayer.futures.rabbitmq.message.EntrustQueryMessage;
import com.waben.stock.datalayer.futures.service.FuturesOrderService;
import com.waben.stock.interfaces.commonapi.retrivefutures.TradeFuturesOverHttp;
import com.waben.stock.interfaces.commonapi.retrivefutures.bean.FuturesGatewayOrder;
import com.waben.stock.interfaces.util.JacksonUtil;

@Component
@RabbitListener(queues = { RabbitmqConfiguration.entrustQueryQueueName })
public class EntrustQueryConsumer {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RabbitmqProducer producer;

	@Autowired
	private FuturesOrderService orderService;

	@RabbitHandler
	public void handlerMessage(String message) {
		logger.info("消费期货委托查询消息:{}", message);
		EntrustQueryMessage messgeObj = JacksonUtil.decode(message, EntrustQueryMessage.class);
		try {
			Long orderId = messgeObj.getOrderId();
			Integer entrustType = messgeObj.getEntrustType();
			Long gatewayOrderId = messgeObj.getGatewayOrderId();
			FuturesGatewayOrder gatewayOrder = TradeFuturesOverHttp.retriveByGatewayId(gatewayOrderId);
			String status = gatewayOrder.getStatus();
			boolean isNeedRetry = true;
			if (entrustType == 1) {
				if ("Cancelled".equals(status)) {
					// 已取消
					orderService.canceledOrder(orderId);
					isNeedRetry = false;
				} else if ("Filled".equals(status) && gatewayOrder.getRemaining().compareTo(BigDecimal.ZERO) > 0) {
					// 部分买入成功
					orderService.partPositionOrder(orderId);
				} else if ("Filled".equals(status) && gatewayOrder.getRemaining().compareTo(BigDecimal.ZERO) == 0) {
					// 持仓中
					orderService.positionOrder(orderId, gatewayOrder.getLastFillPrice());
				}
			} else if (entrustType == 2) {
				if ("Filled".equals(status) && gatewayOrder.getRemaining().compareTo(BigDecimal.ZERO) > 0) {
					// 部分已平仓
					orderService.partUnwindOrder(orderId);
				} else if ("Filled".equals(status) && gatewayOrder.getRemaining().compareTo(BigDecimal.ZERO) == 0) {
					// 已平仓
					orderService.unwindOrder(orderId, gatewayOrder.getLastFillPrice());
				}
			} else if (entrustType == 3) {
				if ("Filled".equals(status) && gatewayOrder.getRemaining().compareTo(BigDecimal.ZERO) > 0) {
					// 部分已平仓
					orderService.partUnwindOrder(orderId);
				} else if ("Filled".equals(status) && gatewayOrder.getRemaining().compareTo(BigDecimal.ZERO) == 0) {
					// 已平仓
					orderService.unwindOrder(orderId, gatewayOrder.getLastFillPrice());
					// TODO 反手以市价买入
				}
			} else {
				logger.error("错误的委托类型!");
				isNeedRetry = false;
			}

			if (isNeedRetry) {
				retry(messgeObj);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			retry(messgeObj);
		}
	}

	private void retry(EntrustQueryMessage messgeObj) {
		try {
			int consumeCount = messgeObj.getConsumeCount();
			if (consumeCount < 60) {
				messgeObj.setConsumeCount(consumeCount + 1);
				Thread.sleep(100);
				producer.sendMessage(RabbitmqConfiguration.entrustQueryQueueName, messgeObj);
			}
		} catch (Exception ex) {
			throw new RuntimeException(RabbitmqConfiguration.entrustQueryQueueName + " message retry exception!", ex);
		}
	}

}
