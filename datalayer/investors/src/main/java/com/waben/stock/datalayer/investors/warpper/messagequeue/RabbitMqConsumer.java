package com.waben.stock.datalayer.investors.warpper.messagequeue;

import com.waben.stock.datalayer.investors.business.BuyRecordBusiness;
import com.waben.stock.datalayer.investors.business.StockBusiness;
import com.waben.stock.datalayer.investors.entity.Investor;
import com.waben.stock.datalayer.investors.reference.StockReference;
import com.waben.stock.datalayer.investors.service.InvestorService;
import com.waben.stock.datalayer.investors.warpper.messagequeue.rabbitmq.EntrustApplyProducer;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.dto.stockcontent.StockDto;
import com.waben.stock.interfaces.enums.EntrustState;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.stock.SecuritiesStockEntrust;
import com.waben.stock.interfaces.pojo.stock.quotation.PositionStock;
import com.waben.stock.interfaces.util.JacksonUtil;
import com.waben.stock.interfaces.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author Created by yuyidi on 2017/11/27.
 * @desc
 */
@Component
public class RabbitMqConsumer {

	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private InvestorService investorService;
	@Autowired
	private StockBusiness stockBusiness;
	@Autowired
	private BuyRecordBusiness buyRecordBusiness;
	@Autowired
	private EntrustApplyProducer entrustProducer;
	@RabbitListener(queues = {"riskPositionSellOut"})
	public void buyInSuccessRisk(PositionStock positionStock) throws InterruptedException {
		logger.info("强制卖出持仓订单数据:{}", JacksonUtil.encode(positionStock));
		SecuritiesStockEntrust securitiesStockEntrust = new SecuritiesStockEntrust();
		StockDto stockDto = stockBusiness.fetchWithExponentByCode(positionStock.getStockCode());
		securitiesStockEntrust.setExponent(stockDto.getStockExponentDto().getExponentCode());
		securitiesStockEntrust.setStockCode(positionStock.getStockCode());
		securitiesStockEntrust.setEntrustNumber(positionStock.getEntrustNumber());
		securitiesStockEntrust.setEntrustPrice(positionStock.getEntrustPrice());
//		String entrustNo = investorService.buyRecordApplySellOut(securitiesStockEntrust, positionStock.getTradeSession());
		String entrustNo = String.valueOf(new Random().nextInt(100));
		logger.info("风控委托申请成功委托编号:{}",entrustNo);
		Investor investor = investorService.findById(positionStock.getInvestorId());
		Thread.sleep(3 * 1000);
		BuyRecordDto buyRecordDto = buyRecordBusiness.entrustApplySellOut(investor, securitiesStockEntrust, entrustNo, positionStock.getWindControlType());
		logger.info("修改订单状态成功:{}",buyRecordDto.getTradeNo());
		securitiesStockEntrust.setTradeSession(positionStock.getTradeSession());
		securitiesStockEntrust.setTradeNo(buyRecordDto.getTradeNo());
		securitiesStockEntrust.setEntrustNo(buyRecordDto.getDelegateNumber());
		securitiesStockEntrust.setEntrustState(EntrustState.HASBEENSUCCESS);
		entrustProducer.entrustApplySellOut(securitiesStockEntrust);
	}
}