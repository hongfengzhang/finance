package com.waben.stock.futuresgateway.yisheng.rabbitmq.consumer;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.future.api.es.external.quote.bean.TapAPIQuoteCommodityInfo;
import com.waben.stock.futuresgateway.yisheng.entity.FuturesContract;
import com.waben.stock.futuresgateway.yisheng.esapi.EsEngine;
import com.waben.stock.futuresgateway.yisheng.rabbitmq.RabbitmqConfiguration;
import com.waben.stock.futuresgateway.yisheng.service.FuturesContractService;
import com.waben.stock.futuresgateway.yisheng.util.JacksonUtil;

@Component
@RabbitListener(queues = { RabbitmqConfiguration.commodityQueueName })
public class CommodityConsumer {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private FuturesContractService contractService;

	@Autowired
	private EsEngine engine;

	@RabbitHandler
	public void handlerMessage(String message) {
		TapAPIQuoteCommodityInfo msgObj = JacksonUtil.decode(message, TapAPIQuoteCommodityInfo.class);
		try {
			String symbol = msgObj.getCommodity().getCommodityNo();
			char commodityType = msgObj.getCommodity().getCommodityType();
			if (commodityType == 'F') {
				// 保存品种信息
				FuturesContract contract = contractService.getContractInfoBySymbol(symbol);
				if (contract != null) {
					contract.setExchange(msgObj.getCommodity().getExchangeNo());
					contract.setMinWave(new BigDecimal(msgObj.getCommodityTickSize()));
					contractService.addContract(contract);
				} else {
					contract = new FuturesContract();
					contract.setSymbol(symbol);
					contract.setExchange(msgObj.getCommodity().getExchangeNo());
					contract.setMinWave(new BigDecimal(msgObj.getCommodityTickSize()));
					contractService.modifyContract(contract);
				}
				// 查询品种合约信息
				if (contract.getEnable() != null && contract.getEnable()) {
					engine.qryContract(msgObj.getCommodity());
				}
			}
		} catch (Exception ex) {
			logger.error("消费Commodity消息异常!", ex);
		}
	}

}
