package com.waben.stock.applayer.operation.warpper.messagequeue;

import com.waben.stock.applayer.operation.business.BuyRecordBusiness;
import com.waben.stock.applayer.operation.business.InvestorBusiness;
import com.waben.stock.applayer.operation.business.StockBusiness;
import com.waben.stock.applayer.operation.service.investor.InvestorService;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.dto.investor.InvestorDto;
import com.waben.stock.interfaces.dto.stockcontent.StockDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.stock.SecuritiesStockEntrust;
import com.waben.stock.interfaces.pojo.stock.quotation.PositionStock;
import com.waben.stock.interfaces.pojo.stock.quotation.StockMarket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Created by yuyidi on 2017/11/27.
 * @desc
 */
@Component
public class RabbitMqConsumer {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private InvestorBusiness investorBusiness;
    @RabbitListener(queues = {"riskPositionSellOut"})
    public void buyInSuccessRisk(PositionStock positionStock) {
        logger.info("强制卖出持仓订单数据:{}",positionStock.toString());

//        long start = System.currentTimeMillis();
//        long end = System.currentTimeMillis();
//        logger.info("执行时间：{}",(end-start));
//        investorBusiness.sellOut(positionStock);
    }

}
