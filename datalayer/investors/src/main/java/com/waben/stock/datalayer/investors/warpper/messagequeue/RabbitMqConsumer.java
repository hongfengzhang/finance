package com.waben.stock.datalayer.investors.warpper.messagequeue;

import com.waben.stock.datalayer.investors.business.BuyRecordBusiness;
import com.waben.stock.datalayer.investors.business.StockBusiness;
import com.waben.stock.datalayer.investors.entity.Investor;
import com.waben.stock.datalayer.investors.reference.StockReference;
import com.waben.stock.datalayer.investors.service.InvestorService;
import com.waben.stock.datalayer.investors.warpper.messagequeue.rabbitmq.EntrustApplyProducer;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.dto.manage.BannerDto;
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
		securitiesStockEntrust.setExponent(stockDto.getExponent().getExponentCode());
		securitiesStockEntrust.setStockCode(positionStock.getStockCode());
		securitiesStockEntrust.setEntrustNumber(positionStock.getEntrustNumber());
		securitiesStockEntrust.setEntrustPrice(positionStock.getEntrustPrice());
		securitiesStockEntrust.setBuyRecordId(positionStock.getBuyRecordId());
		String entrustNo = investorService.buyRecordApplySellOut(securitiesStockEntrust, positionStock.getTradeSession());
		logger.info("风控委托申请成功委托编号:{}",entrustNo);
		Investor investor = investorService.findById(positionStock.getInvestorId());
		BuyRecordDto buyRecordDto = buyRecordBusiness.entrustApplySellOut(investor, securitiesStockEntrust, entrustNo, positionStock.getWindControlType());
		logger.info("修改订单状态成功:{}",buyRecordDto.getTradeNo());
		securitiesStockEntrust.setTradeSession(positionStock.getTradeSession());
		securitiesStockEntrust.setTradeNo(buyRecordDto.getTradeNo());
		securitiesStockEntrust.setEntrustNo(buyRecordDto.getDelegateNumber());
		securitiesStockEntrust.setEntrustState(EntrustState.HASBEENSUCCESS);
		entrustProducer.entrustApplySellOut(securitiesStockEntrust);
	}

	@RabbitListener(queues = {"entrustApplyWithdraw"})
	public void entrustApplyWithdraw(SecuritiesStockEntrust securitiesStockEntrust) throws InterruptedException {
		logger.info("委托撤单订单数据:{}", JacksonUtil.encode(securitiesStockEntrust));
		String entrustNo = investorService.buyRecordApplyWithdraw(securitiesStockEntrust);
		BuyRecordDto buyRecordDto = buyRecordBusiness.entrustApplyWithdraw(entrustNo, securitiesStockEntrust.getBuyRecordId());
		logger.info("修改订单撤单锁定状态成功:{}",buyRecordDto.getTradeNo());
		securitiesStockEntrust.setTradeNo(buyRecordDto.getTradeNo());
		securitiesStockEntrust.setEntrustNo(buyRecordDto.getDelegateNumber());
		securitiesStockEntrust.setEntrustState(EntrustState.REPORTEDTOWITHDRAW);
		entrustProducer.entrustQueryWithdraw(securitiesStockEntrust);
	}

	@RabbitListener(queues = {"voluntarilyApplyBuyIn"})
	public void voluntarilyEntrustApplyBuyIn(SecuritiesStockEntrust securitiesStockEntrust) throws InterruptedException {
		logger.info("自动买入订单数据:{}", JacksonUtil.encode(securitiesStockEntrust));
		BuyRecordDto buyRecordDto = investorService.buyIn(securitiesStockEntrust);
		logger.info("委托买入成功：{}",JacksonUtil.encode(buyRecordDto));
	}
}