package com.waben.stock.futuresgateway.yisheng.rabbitmq.consumer;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.future.api.es.external.quote.bean.TapAPIQuoteWhole;
import com.waben.stock.futuresgateway.yisheng.entity.FuturesContractQuote;
import com.waben.stock.futuresgateway.yisheng.rabbitmq.RabbitmqConfiguration;
import com.waben.stock.futuresgateway.yisheng.service.FuturesContractQuoteService;
import com.waben.stock.futuresgateway.yisheng.util.JacksonUtil;

@Component
@RabbitListener(queues = { RabbitmqConfiguration.quoteQueueName }, containerFactory = "quoteListenerContainerFactory")
public class QuoteConsumer {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private FuturesContractQuoteService quoteService;

	@RabbitHandler
	public void handlerMessage(String message) {
		TapAPIQuoteWhole msgObj = JacksonUtil.decode(message, TapAPIQuoteWhole.class);
		try {
			String commodityNo = msgObj.getContract().getCommodity().getCommodityNo();
			String contractNo = msgObj.getContract().getContractNo1();
			char commodityType = msgObj.getContract().getCommodity().getCommodityType();
			if (commodityType == 'F') {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				// 保存行情信息
				FuturesContractQuote quote = new FuturesContractQuote();
				quote.setAskPrice(JacksonUtil.encode(msgObj.getQAskPrice()));
				quote.setAskQty(JacksonUtil.encode(msgObj.getQAskQty()));
				quote.setAveragePrice(new BigDecimal(msgObj.getQAveragePrice()));
				quote.setBidPrice(JacksonUtil.encode(msgObj.getQBidPrice()));
				quote.setBidQty(JacksonUtil.encode(msgObj.getQBidQty()));
				quote.setChangeRate(new BigDecimal(msgObj.getQChangeRate()));
				quote.setChangeSpeed(new BigDecimal(msgObj.getQChangeSpeed()));
				quote.setChangeValue(new BigDecimal(msgObj.getQChangeValue()));
				quote.setClosingPrice(new BigDecimal(msgObj.getQClosingPrice()));
				quote.setCommodityNo(commodityNo);
				quote.setContractNo(contractNo);
				quote.setCurrDelta(new BigDecimal(msgObj.getQCurrDelta()));
				quote.setD5AvgQty(msgObj.getQ5DAvgQty());
				quote.setDateTimeStamp(msgObj.getDateTimeStamp());
				quote.setHighPrice(new BigDecimal(msgObj.getQHighPrice()));
				quote.setHisHighPrice(new BigDecimal(msgObj.getQHisHighPrice()));
				quote.setHisLowPrice(new BigDecimal(msgObj.getQHisLowPrice()));
				quote.setImpliedAskPrice(new BigDecimal(msgObj.getQImpliedAskPrice()));
				quote.setImpliedAskQty(msgObj.getQImpliedAskQty());
				quote.setImpliedBidPrice(new BigDecimal(msgObj.getQImpliedBidPrice()));
				quote.setImpliedBidQty(msgObj.getQImpliedBidQty());
				quote.setInsideQty(msgObj.getQInsideQty());
				quote.setLastPrice(new BigDecimal(msgObj.getQLastPrice()));
				quote.setLastQty(msgObj.getQLastQty());
				quote.setLimitDownPrice(new BigDecimal(msgObj.getQLimitDownPrice()));
				quote.setLimitUpPrice(new BigDecimal(msgObj.getQLimitUpPrice()));
				quote.setLowPrice(new BigDecimal(msgObj.getQLowPrice()));
				quote.setNegotiableValue(new BigDecimal(msgObj.getQNegotiableValue()));
				quote.setOpeningPrice(new BigDecimal(msgObj.getQOpeningPrice()));
				quote.setOutsideQty(msgObj.getQOutsideQty());
				quote.setPeRatio(new BigDecimal(msgObj.getQPERatio()));
				quote.setPositionQty(msgObj.getQPositionQty());
				quote.setPositionTrend(msgObj.getQPositionTrend());
				quote.setPreClosingPrice(new BigDecimal(msgObj.getQPreClosingPrice()));
				quote.setPreDelta(new BigDecimal(msgObj.getQPreDelta()));
				quote.setPrePositionQty(msgObj.getQPrePositionQty());
				quote.setPreSettlePrice(new BigDecimal(msgObj.getQPreSettlePrice()));
				quote.setSettlePrice(new BigDecimal(msgObj.getQSettlePrice()));
				quote.setSwing(new BigDecimal(msgObj.getQSwing()));
				Date nowTime = sdf.parse(msgObj.getDateTimeStamp());
				quote.setTime(nowTime);
				quote.setTotalAskQty(msgObj.getQTotalAskQty());
				quote.setTotalBidQty(msgObj.getQTotalBidQty());
				quote.setTotalQty(msgObj.getQTotalQty());
				quote.setTotalTurnover(new BigDecimal(msgObj.getQTotalTurnover()));
				quote.setTotalValue(new BigDecimal(msgObj.getQTotalValue()));
				quote.setTurnoverRate(new BigDecimal(msgObj.getQTurnoverRate()));
				quoteService.addFuturesContractQuote(quote);
			}
		} catch (Exception ex) {
			logger.error("消费易盛Quote消息异常!", ex);
		}
	}

}
