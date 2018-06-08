package com.waben.stock.futuresgateway.yingtou.rabbitmq.consumer;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.waben.stock.futuresgateway.yingtou.cache.CommonDataCache;
import com.waben.stock.futuresgateway.yingtou.entity.FuturesContract;
import com.waben.stock.futuresgateway.yingtou.rabbitmq.RabbitmqConfiguration;
import com.waben.stock.futuresgateway.yingtou.rabbitmq.message.TickPriceMessage;
import com.waben.stock.futuresgateway.yingtou.service.FuturesContractService;
import com.waben.stock.futuresgateway.yingtou.util.JacksonUtil;

@Component
@RabbitListener(queues = { RabbitmqConfiguration.tickPriceQueueName })
public class TickPriceConsumer {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private FuturesContractService futuresContractService;

	@RabbitHandler
	public void handlerMessage(String message) {
		logger.info("TickPrice消息:{}", message);
		TickPriceMessage msgObj = JacksonUtil.decode(message, TickPriceMessage.class);
		try {
			int tickerId = msgObj.getTickerId();
			int field = msgObj.getField();
			double price = msgObj.getPrice();
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
			// step 3 : 更新期货合约的相关价格
			boolean isNeedUpdate = true;
			if (field == 1) {
				contract.setBigPrice(new BigDecimal(price));
			} else if (field == 2) {
				contract.setAskPrice(new BigDecimal(price));
			} else if (field == 4) {
				contract.setLastPrice(new BigDecimal(price));
			} else if (field == 6) {
				contract.setHighPrice(new BigDecimal(price));
			} else if (field == 7) {
				contract.setLowPrice(new BigDecimal(price));
			} else if (field == 9) {
				contract.setClosePrice(new BigDecimal(price));
			} else if (field == 14) {
				contract.setOpenPrice(new BigDecimal(price));
			}else {
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
