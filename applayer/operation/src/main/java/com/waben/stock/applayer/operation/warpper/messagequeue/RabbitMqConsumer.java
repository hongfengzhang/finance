package com.waben.stock.applayer.operation.warpper.messagequeue;

import com.waben.stock.applayer.operation.business.InvestorBusiness;
import com.waben.stock.applayer.operation.business.StockBusiness;
import com.waben.stock.applayer.operation.service.investor.InvestorService;
import com.waben.stock.interfaces.dto.investor.InvestorDto;
import com.waben.stock.interfaces.dto.stockcontent.StockDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.stock.SecuritiesStockEntrust;
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
    private InvestorBusiness investorBusiness;
    @Autowired
    private StockBusiness stockBusiness;
    @RabbitListener(queues = {"riskPositionSellOut"})
    public void buyInSuccessRisk(SecuritiesStockEntrust securitiesStockEntrust) {
        StockDto stockDto = stockBusiness.fetchWithExponentByCode(securitiesStockEntrust.getStockCode());
        securitiesStockEntrust.setExponent(stockDto.getStockExponentDto().getExponentCode());
        logger.info("委托买出成功:{}", securitiesStockEntrust.getTradeNo());
        logger.info("investor:{}",securitiesStockEntrust.getInvestor());
        if(securitiesStockEntrust.getTradeSession()==null) {
            logger.info("investor:{}",securitiesStockEntrust.getTradeSession());
        }else {
            investorBusiness.sellOut(securitiesStockEntrust);
            logger.info("investor:{}",securitiesStockEntrust.getTradeSession());
        }
        //
    }

}
