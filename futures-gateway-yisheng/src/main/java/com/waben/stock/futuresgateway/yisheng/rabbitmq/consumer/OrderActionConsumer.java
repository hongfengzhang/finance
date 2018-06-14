package com.waben.stock.futuresgateway.yisheng.rabbitmq.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.waben.stock.futuresgateway.yisheng.rabbitmq.RabbitmqConfiguration;

@Component
@RabbitListener(queues = { RabbitmqConfiguration.orderActionQueueName })
public class OrderActionConsumer {

	Logger logger = LoggerFactory.getLogger(getClass());

	@RabbitHandler
	public void handlerMessage(String message) {
		logger.info("消费易盛OrderAction通知消息:" + message);
		// OrderActionMessage msgObj = JacksonUtil.decode(message,
		// OrderActionMessage.class);
		try {
			// TODO 消费消息
		} catch (Exception ex) {
			logger.error("消费易盛OrderAction通知消息异常!", ex);
		}
	}

}
