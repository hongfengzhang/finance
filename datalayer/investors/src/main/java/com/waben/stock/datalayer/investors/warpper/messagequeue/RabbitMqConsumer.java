package com.waben.stock.datalayer.investors.warpper.messagequeue;

import com.waben.stock.datalayer.investors.business.BuyRecordBusiness;
import com.waben.stock.datalayer.investors.business.StockBusiness;
import com.waben.stock.datalayer.investors.container.StockApplyEntrustBuyInContainer;
import com.waben.stock.datalayer.investors.container.StockApplyEntrustSellOutContainer;
import com.waben.stock.datalayer.investors.entity.Investor;
import com.waben.stock.datalayer.investors.reference.BuyRecordReference;
import com.waben.stock.datalayer.investors.reference.StockReference;
import com.waben.stock.datalayer.investors.service.InvestorService;
import com.waben.stock.datalayer.investors.warpper.messagequeue.rabbitmq.EntrustApplyProducer;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.dto.manage.BannerDto;
import com.waben.stock.interfaces.dto.stockcontent.StockDto;
import com.waben.stock.interfaces.enums.BuyRecordState;
import com.waben.stock.interfaces.enums.EntrustState;
import com.waben.stock.interfaces.enums.WindControlType;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
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
	private StockApplyEntrustSellOutContainer stockApplyEntrustSellOutContainer;
	@RabbitListener(queues = {"riskPositionSellOut"})
	public void buyInSuccessRisk(PositionStock positionStock) throws InterruptedException {
		logger.info("强制卖出持仓订单数据:{}", JacksonUtil.encode(positionStock));
		BuyRecordDto buyRecordDto = buyRecordBusiness.findById(positionStock.getBuyRecordId());
		logger.info("修改前订单状态位卖出申请：{}",buyRecordDto.getState());
		if(!BuyRecordState.SELLAPPLY.equals(buyRecordDto.getState())) {
			buyRecordDto.setWindControlType(WindControlType.getByIndex(positionStock.getWindControlType()));
			buyRecordDto.setUpdateTime(new Date());
			BuyRecordDto result = buyRecordBusiness.revisionState(buyRecordDto);
			logger.info("修改后订单状态位卖出申请：{}",result.getState());
		}
		SecuritiesStockEntrust securitiesStockEntrust = new SecuritiesStockEntrust();
		StockDto stockDto = stockBusiness.fetchWithExponentByCode(positionStock.getStockCode());
		securitiesStockEntrust.setExponent(stockDto.getExponent().getExponentCode());
		securitiesStockEntrust.setStockCode(positionStock.getStockCode());
		securitiesStockEntrust.setEntrustNumber(positionStock.getEntrustNumber());
		securitiesStockEntrust.setEntrustPrice(positionStock.getEntrustPrice());
		securitiesStockEntrust.setBuyRecordId(positionStock.getBuyRecordId());
		securitiesStockEntrust.setWindControlType(positionStock.getWindControlType());
		securitiesStockEntrust.setTradeNo(positionStock.getTradeNo());
		stockApplyEntrustSellOutContainer.add(securitiesStockEntrust);
	}

	@RabbitListener(queues = {"entrustApplyWithdraw"})
	public void entrustApplyWithdraw(SecuritiesStockEntrust securitiesStockEntrust) throws InterruptedException {
		logger.info("委托撤单订单数据:{}", JacksonUtil.encode(securitiesStockEntrust));
		try{
			String entrustNo = investorService.buyRecordApplyWithdraw(securitiesStockEntrust);
			BuyRecordDto buyRecordDto = buyRecordBusiness.entrustApplyWithdraw(entrustNo, securitiesStockEntrust.getBuyRecordId());
			logger.info("修改订单撤单锁定状态成功:{}",buyRecordDto.getTradeNo());
			securitiesStockEntrust.setTradeNo(buyRecordDto.getTradeNo());
			securitiesStockEntrust.setEntrustNo(buyRecordDto.getDelegateNumber());
			securitiesStockEntrust.setEntrustState(EntrustState.REPORTEDTOWITHDRAW);
			entrustProducer.entrustQueryWithdraw(securitiesStockEntrust);
			logger.info("撤单成功：{}",JacksonUtil.encode(buyRecordDto));
		}catch (Exception ex) {
			logger.error("撤单失败：{}",ex.getMessage());
		}
	}

	@RabbitListener(queues = {"voluntarilyApplyBuyIn"})
	public void voluntarilyEntrustApplyBuyIn(SecuritiesStockEntrust securitiesStockEntrust) throws InterruptedException {
		logger.info("自动买入订单数据:{}", JacksonUtil.encode(securitiesStockEntrust));
		stockApplyEntrustBuyInContainer.add(securitiesStockEntrust);
	}

	@RabbitListener(queues = {"voluntarilyApplySellOut"})
	public void voluntarilyEntrustApplySellOut(SecuritiesStockEntrust securitiesStockEntrust) throws InterruptedException {
		logger.info("自动卖出订单数据:{}", JacksonUtil.encode(securitiesStockEntrust));
		stockApplyEntrustSellOutContainer.add(securitiesStockEntrust);
	}
}