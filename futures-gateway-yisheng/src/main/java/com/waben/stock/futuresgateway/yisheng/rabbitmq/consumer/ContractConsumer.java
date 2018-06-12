package com.waben.stock.futuresgateway.yisheng.rabbitmq.consumer;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.waben.stock.futuresgateway.yisheng.entity.FuturesContract;
import com.waben.stock.futuresgateway.yisheng.esapi.EsEngine;
import com.waben.stock.futuresgateway.yisheng.rabbitmq.RabbitmqConfiguration;
import com.waben.stock.futuresgateway.yisheng.rabbitmq.message.TapAPIQuoteContractInfoWithSession;
import com.waben.stock.futuresgateway.yisheng.service.FuturesContractService;
import com.waben.stock.futuresgateway.yisheng.util.JacksonUtil;

@Component
@RabbitListener(queues = { RabbitmqConfiguration.contractQueueName })
public class ContractConsumer {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private FuturesContractService contractService;

	@Autowired
	private EsEngine engine;

	private Map<String, String> contractNoMap = new HashMap<String, String>();

	@RabbitHandler
	public void handlerMessage(String message) {
		TapAPIQuoteContractInfoWithSession msgObj = JacksonUtil.decode(message,
				TapAPIQuoteContractInfoWithSession.class);
		try {
			String commodityNo = msgObj.getContract().getCommodity().getCommodityNo();
			if (contractNoMap.containsKey(commodityNo)) {
				String value = contractNoMap.get(commodityNo);
			} else {
				contractNoMap.put(commodityNo, msgObj.getContract().getContractNo1() + "_" + msgObj.getSessionID());
			}
		} catch (Exception ex) {
			logger.error("消费Contract消息异常!", ex);
		}
	}

}
