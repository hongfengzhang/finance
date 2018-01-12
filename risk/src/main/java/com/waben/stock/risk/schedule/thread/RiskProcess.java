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
import java.util.List;
import java.util.concurrent.Callable;

public class RiskProcess implements Callable<List<SecuritiesStockEntrust>>{
    private PositionSellOutProducer positionProducer = ApplicationContextBeanFactory.getBean(PositionSellOutProducer.class);
    Logger logger = LoggerFactory.getLogger(getClass());
    private StockMarket stockMarket;
    private List<SecuritiesStockEntrust> riskBuyInStocks;

    public RiskProcess(StockMarket stockMarket, List<SecuritiesStockEntrust> riskBuyInStocks) {
        this.stockMarket = stockMarket;
        this.riskBuyInStocks = riskBuyInStocks;
    }

    @Override
    public List<SecuritiesStockEntrust> call() {
        logger.info("run==============================================");
        List<SecuritiesStockEntrust> counts = new ArrayList<>();
        logger.info("size:{}",riskBuyInStocks.size());
        for (SecuritiesStockEntrust riskBuyInStock : riskBuyInStocks) {
            // 遍历容器内持仓中的点买交易订单
            // 判断  最新行情价格与 当前持仓订单买入价格   是否达到止盈或止损点位  若 达到则 执行强制卖出  卖出跌停价
            BigDecimal lastPrice = stockMarket.getLastPrice();
            BigDecimal lossPosition = riskBuyInStock.getLossPosition();
            BigDecimal profitPosition = riskBuyInStock.getProfitPosition();
            if(profitPosition.compareTo(lastPrice)==-1||lossPosition.compareTo(lastPrice)==1) {
                counts.add(riskBuyInStock);
            }
            logger.info("TradeSession:{}",riskBuyInStock.getTradeSession());
            positionProducer.riskPositionSellOut(riskBuyInStock);
        }
        return counts;
    }
}
