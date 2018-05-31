package com.waben.stock.futuresgateway.rabbitmq.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.waben.stock.futuresgateway.cache.CommonDataCache;
import com.waben.stock.futuresgateway.entity.FuturesContract;
import com.waben.stock.futuresgateway.rabbitmq.RabbitmqConfiguration;
import com.waben.stock.futuresgateway.rabbitmq.message.TickSizeMessage;
import com.waben.stock.futuresgateway.service.FuturesContractService;
import com.waben.stock.futuresgateway.util.JacksonUtil;

@Component
@RabbitListener(queues = { RabbitmqConfiguration.tickSizeQueueName })
public class TickSizeConsumer {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private FuturesContractService futuresContractService;

	@RabbitHandler
	public void handlerMessage(String message) {
		logger.info("TickSize消息:{}", message);
		TickSizeMessage msgObj = JacksonUtil.decode(message, TickSizeMessage.class);
		try {
			int tickerId = msgObj.getTickerId();
			int field = msgObj.getField();
			int size = msgObj.getSize();
			// step 1 : 获取期货合约的ID
			Long contractId = Long.valueOf(tickerId);
			String tickerIdStr = String.valueOf(tickerId);
			if (tickerIdStr.length() > 3) {
				contractId = Long.parseLong(tickerIdStr.substring(3));
			}
			// step 2 : 获取期货合约
			FuturesContract contract = CommonDataCache.contractMap.get(contractId);
			if (contract == null) {
				contract = futuresContractService.getContractInfo(contractId);
			}
			// step 3 : 更新期货合约的相关size
			boolean isNeedUpdate = true;
			if (field == 0) {
				contract.setBidSize(size);
			} else if (field == 3) {
				contract.setAskSize(size);
			} else if (field == 5) {
				contract.setLastSize(size);
			} else if (field == 8) {
				contract.setVolume(size);
			} else {
				isNeedUpdate = false;
			}
			if (contract != null && isNeedUpdate) {
				futuresContractService.modifyContract(contract);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
