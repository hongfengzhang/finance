package com.waben.stock.risk.init;

import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.enums.EntrustState;
import com.waben.stock.interfaces.pojo.stock.SecuritiesStockEntrust;
import com.waben.stock.interfaces.pojo.stock.quotation.PositionStock;
import com.waben.stock.risk.business.BuyRecordBusiness;
import com.waben.stock.risk.business.StockBusiness;
import com.waben.stock.risk.container.PositionStockContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by yuyidi on 2017/12/14.
 * @desc
 */
@Component
//@Order(Ordered.LOWEST_PRECEDENCE+100)
public class PositionStockInitialize implements CommandLineRunner {

    @Autowired
    private BuyRecordBusiness buyRecordBusiness;

    @Autowired
    private PositionStockContainer positionStockContainer;
    Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void run(String... args) throws Exception {
        List<BuyRecordDto> buyRecords = buyRecordBusiness.buyRecordsWithPositionStock();
        logger.info("获取持仓交易记录个数：{}", buyRecords.size());
        for (BuyRecordDto buyRecord : buyRecords) {
            PositionStock positionStock = new PositionStock();
            positionStock.setBuyRecordId(buyRecord.getId());
            positionStock.setBuyingPrice(buyRecord.getBuyingPrice());
            positionStock.setStockCode(buyRecord.getStockCode());
            positionStock.setStockName(buyRecord.getStockName());
            positionStock.setLossPosition(buyRecord.getLossPosition());
            positionStock.setProfitPosition(buyRecord.getProfitPosition());
            positionStock.setInvestorId(buyRecord.getInvestorId());
            positionStock.setBuyingTime(buyRecord.getBuyingTime());
            positionStock.setTradeSession("70001553");
            positionStock.setDeferred(buyRecord.getDeferred());
            positionStockContainer.add(positionStock);
            logger.info("TradeSession:{}",positionStock.getTradeSession());
        }
    }
}
