package com.waben.stock.futuresgateway.yingtou.rabbitmq.consumer;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.waben.stock.futuresgateway.yingtou.cache.CommonDataCache;
import com.waben.stock.futuresgateway.yingtou.cache.RedisCache;
import com.waben.stock.futuresgateway.yingtou.entity.FuturesContract;
import com.waben.stock.futuresgateway.yingtou.pojo.FuturesContractLineData;
import com.waben.stock.futuresgateway.yingtou.rabbitmq.RabbitmqConfiguration;
import com.waben.stock.futuresgateway.yingtou.rabbitmq.message.HistoricalDataMessage;
import com.waben.stock.futuresgateway.yingtou.service.FuturesContractService;
import com.waben.stock.futuresgateway.yingtou.twsapi.TwsConstant;
import com.waben.stock.futuresgateway.yingtou.util.JacksonUtil;

@Component
@RabbitListener(queues = {
		RabbitmqConfiguration.historicalDataQueueName }, containerFactory = "historicalDataListenerContainerFactory")
public class HistoricalDataConsumer {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private FuturesContractService futuresContractService;

	@Autowired
	private RedisCache redisCache;
	
	@RabbitHandler
	public void handlerMessage(String message) {
		// SimpleDateFormat并发存在问题
		SimpleDateFormat daySdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat timeFullSdf = new SimpleDateFormat("yyyyMMdd  HH:mm:ss");
		SimpleDateFormat keySdf = new SimpleDateFormat("yyyyMMddHHmmss");
		
		logger.info("HistoricalData消息:{}", message);
		HistoricalDataMessage msgObj = JacksonUtil.decode(message, HistoricalDataMessage.class);
		int reqId = msgObj.getReqId();
		String date = msgObj.getDate();
		double open = msgObj.getOpen();
		double high = msgObj.getHigh();
		double low = msgObj.getLow();
		double close = msgObj.getClose();
		int volume = msgObj.getVolume();
		int count = msgObj.getCount();
		try {
			// step 1 : 获取期货合约的ID
			Long contractId = Long.valueOf(reqId);
			String tickerIdStr = String.valueOf(reqId);
			if (tickerIdStr.length() > 3) {
				contractId = Long.parseLong(tickerIdStr.substring(3));
			}
			// step 2 : 获取期货合约
			FuturesContract contract = CommonDataCache.contractMap.get(contractId);
			if (contract == null) {
				contract = futuresContractService.getContractInfo(contractId);
			}
			// step 3 : 保存历史行情数据到redis
			try {
				String redisKey = null;
				SimpleDateFormat sdf = timeFullSdf;
				if (String.valueOf(reqId).startsWith(TwsConstant.TimeLine_TickerId_Prefix)) {
					// 分时图
					redisKey = TwsConstant.TimeLine_RedisKey;
				} else if (String.valueOf(reqId).startsWith(TwsConstant.DayLine_TickerId_Prefix)) {
					// 日K线
					sdf = daySdf;
					redisKey = TwsConstant.DayLine_RedisKey;
				} else if (String.valueOf(reqId).startsWith(TwsConstant.Min1Line_TickerId_Prefix)) {
					// 1分钟K线
					redisKey = TwsConstant.Min1Line_RedisKey;
				} else if (String.valueOf(reqId).startsWith(TwsConstant.Mins3Line_TickerId_Prefix)) {
					// 3分钟K线
					redisKey = TwsConstant.Mins3Line_RedisKey;
				} else if (String.valueOf(reqId).startsWith(TwsConstant.Mins5Line_TickerId_Prefix)) {
					// 5分钟K线
					redisKey = TwsConstant.Mins5Line_RedisKey;
				} else if (String.valueOf(reqId).startsWith(TwsConstant.Mins15Line_TickerId_Prefix)) {
					// 15分钟K线
					redisKey = TwsConstant.Mins15Line_RedisKey;
				}
				if (redisKey != null && date.indexOf("finished") < 0) {
					FuturesContractLineData data = new FuturesContractLineData();
					data.setOpen(new BigDecimal(String.valueOf(open)));
					data.setClose(new BigDecimal(String.valueOf(close)));
					data.setHigh(new BigDecimal(String.valueOf(high)));
					data.setLow(new BigDecimal(String.valueOf(low)));
					data.setVolume(volume);
					data.setCount(count);
					data.setTime(sdf.parse(date));
					redisCache.hset(String.format(redisKey, contract.getId() + "_" + contract.getSymbol()),
							keySdf.format(data.getTime()), JacksonUtil.encode(data));
				}
				Thread.sleep(100);
			} catch (ParseException ex) {
				System.out.println(ex.getMessage() + ":" + date + ":" + reqId);
				ex.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage() + ":" + date + ":" + reqId);
			ex.printStackTrace();
		}
	}

}
