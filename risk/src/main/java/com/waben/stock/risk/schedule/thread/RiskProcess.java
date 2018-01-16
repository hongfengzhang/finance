package com.waben.stock.risk.schedule.thread;

import com.waben.stock.interfaces.pojo.stock.SecuritiesStockEntrust;
import com.waben.stock.interfaces.pojo.stock.quotation.PositionStock;
import com.waben.stock.interfaces.pojo.stock.quotation.StockMarket;
import com.waben.stock.risk.warpper.ApplicationContextBeanFactory;
import com.waben.stock.risk.warpper.messagequeue.rabbitmq.EntrustProducer;
import com.waben.stock.risk.warpper.messagequeue.rabbitmq.PositionSellOutProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

public class RiskProcess implements Callable<List<PositionStock>>{
    private PositionSellOutProducer positionProducer = ApplicationContextBeanFactory.getBean(PositionSellOutProducer.class);
    Logger logger = LoggerFactory.getLogger(getClass());
    private StockMarket stockMarket;
    private List<PositionStock> positionStock;

    public RiskProcess(StockMarket stockMarket, List<PositionStock> positionStocks) {
        this.stockMarket = stockMarket;
        this.positionStock = positionStocks;
    }

    @Override
    public List<PositionStock> call() {
        logger.info("run==============================================");
        List<PositionStock> counts = new ArrayList<>();
        logger.info("size:{}",positionStock.size());
        for (PositionStock riskBuyInStock : positionStock) {
            // 遍历容器内持仓中的点买交易订单
            // 判断  最新行情价格与 当前持仓订单买入价格   是否达到止盈或止损点位  若 达到则 执行强制卖出  卖出跌停价
            BigDecimal lastPrice = stockMarket.getLastPrice();
            BigDecimal lossPosition = riskBuyInStock.getLossPosition();
            BigDecimal profitPosition = riskBuyInStock.getProfitPosition();
            if(profitPosition.compareTo(lastPrice)==-1||lossPosition.compareTo(lastPrice)==1) {
                counts.add(riskBuyInStock);
            }
            //如果没有递延费，达到交易日后，强制卖出
            Date buyingTime = riskBuyInStock.getBuyingTime();
            Long strategyType = riskBuyInStock.getStrategyTypeId();
            Boolean isDeferred = riskBuyInStock.getDeferred();
            logger.info("TradeSession:{}",riskBuyInStock.getTradeSession());
            if(riskBuyInStock.getStockCode().equals("000005")) {
                positionProducer.riskPositionSellOut(riskBuyInStock);
                counts.add(riskBuyInStock);
            }
        }
        return counts;
    }
}
