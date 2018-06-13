package com.waben.stock.futuresgateway.yisheng.rabbitmq.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.waben.stock.futuresgateway.yisheng.rabbitmq.RabbitmqConfiguration;
import com.waben.stock.futuresgateway.yisheng.rabbitmq.message.DeleteQuoteMessage;
import com.waben.stock.futuresgateway.yisheng.service.FuturesContractQuoteService;
import com.waben.stock.futuresgateway.yisheng.util.JacksonUtil;

@Component
@RabbitListener(queues = { RabbitmqConfiguration.deleteQuoteQueueName }, containerFactory = "deleteQuoteListenerContainerFactory")
public class DeleteQuoteConsumer {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private FuturesContractQuoteService quoteService;

	@RabbitHandler
	public void handlerMessage(String message) {
		DeleteQuoteMessage msgObj = JacksonUtil.decode(message, DeleteQuoteMessage.class);
		try {
			Long quoteId = msgObj.getQuoteId();
			quoteService.deleteFuturesContractQuote(quoteId);
		} catch (Exception ex) {
			logger.error("消费删除易盛Quote消息异常!", ex);
		}
	}

}
