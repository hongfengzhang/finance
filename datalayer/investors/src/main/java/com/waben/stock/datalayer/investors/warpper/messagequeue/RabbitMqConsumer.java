package com.waben.stock.datalayer.investors.warpper.messagequeue;

import com.waben.stock.datalayer.investors.business.BuyRecordBusiness;
import com.waben.stock.datalayer.investors.business.StockBusiness;
import com.waben.stock.datalayer.investors.container.StockApplyEntrustBuyInContainer;
import com.waben.stock.datalayer.investors.entity.Investor;
import com.waben.stock.datalayer.investors.reference.StockReference;
import com.waben.stock.datalayer.investors.service.InvestorService;
import com.waben.stock.datalayer.investors.warpper.messagequeue.rabbitmq.EntrustApplyProducer;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.dto.manage.BannerDto;
import com.waben.stock.interfaces.dto.stockcontent.StockDto;
import com.waben.stock.interfaces.enums.BuyRecordState;
import com.waben.stock.interfaces.enums.EntrustState;
import com.waben.stock.interfaces.exception.ExecptionHandler;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.stock.SecuritiesStockEntrust;
import com.waben.stock.interfaces.pojo.stock.quotation.PositionStock;
import com.waben.stock.interfaces.util.JacksonUtil;
import com.waben.stock.interfaces.util.RandomUtil;
import javassist.bytecode.stackmap.BasicBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
	@Autowired
	private StockApplyEntrustBuyInContainer stockApplyEntrustBuyInContainer;
	@Autowired
	private StockApplyEntrustBuyInContainer stockApplyEntrustSellOutContainer;

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
		securitiesStockEntrust.setBuyRecordState(BuyRecordState.HOLDPOSITION);
		investorService.voluntarilyApplySellOut(securitiesStockEntrust,positionStock.getWindControlType());
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
//		logger.info("自动买入订单数据:{}", JacksonUtil.encode(securitiesStockEntrust));
//		try {
//			BuyRecordDto buyRecordDto = investorService.buyIn(securitiesStockEntrust);
//			logger.info("委托买入成功：{}",JacksonUtil.encode(buyRecordDto));
//		}catch (ServiceException serviceException) {
//			logger.info("服务异常：{}",serviceException.getMessage());
//		}
		stockApplyEntrustBuyInContainer.add(securitiesStockEntrust);
	}

	@RabbitListener(queues = {"voluntarilyApplySellOut"})
	public void voluntarilyEntrustApplySellOut(SecuritiesStockEntrust securitiesStockEntrust) throws InterruptedException {
//		logger.info("自动卖出订单数据:{}", JacksonUtil.encode(securitiesStockEntrust));
//		try {
//			BuyRecordDto buyRecordDto = investorService.voluntarilyApplySellOut(securitiesStockEntrust);
//			logger.info("委托卖出成功：{}",JacksonUtil.encode(buyRecordDto));
//		}catch (ServiceException serviceException) {
//			logger.info("服务异常：{}",serviceException.getMessage());
//		}
		stockApplyEntrustSellOutContainer.add(securitiesStockEntrust);
	}
}