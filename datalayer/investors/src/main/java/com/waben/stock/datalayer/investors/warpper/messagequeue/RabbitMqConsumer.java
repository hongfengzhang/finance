package com.waben.stock.datalayer.investors.warpper.messagequeue;

import com.waben.stock.datalayer.investors.business.BuyRecordBusiness;
import com.waben.stock.datalayer.investors.business.StockBusiness;
import com.waben.stock.datalayer.investors.entity.Investor;
import com.waben.stock.datalayer.investors.reference.StockReference;
import com.waben.stock.datalayer.investors.service.InvestorService;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.dto.stockcontent.StockDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.stock.SecuritiesStockEntrust;
import com.waben.stock.interfaces.pojo.stock.quotation.PositionStock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

	@RabbitListener(queues = {"riskPositionSellOut"})
	public void buyInSuccessRisk(PositionStock positionStock) {
		logger.info("强制卖出持仓订单数据:{}",positionStock.toString());
		SecuritiesStockEntrust securitiesStockEntrust = new SecuritiesStockEntrust();
		StockDto stockDto = stockBusiness.fetchWithExponentByCode(positionStock.getStockCode());
		securitiesStockEntrust.setExponent(stockDto.getStockExponentDto().getExponentCode());
		securitiesStockEntrust.setStockCode(positionStock.getStockCode());
		securitiesStockEntrust.setEntrustNumber(positionStock.getEntrustNumber());
		securitiesStockEntrust.setEntrustPrice(positionStock.getEntrustPrice());
		String entrustNo = investorService.buyRecordApplySellOut(securitiesStockEntrust, positionStock.getTradeSession());
		logger.info("委托申请成功委托编号:{}",entrustNo);
		Investor investor = investorService.findById(positionStock.getInvestorId());
		BuyRecordDto buyRecordDto = buyRecordBusiness.entrustApplySellOut(investor, securitiesStockEntrust, entrustNo, positionStock.getWindControlType());
		logger.info("修改订单状态成功:{}",buyRecordDto.getTradeNo());
	}
}