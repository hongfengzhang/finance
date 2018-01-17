package com.waben.stock.risk.schedule.thread;

import com.waben.stock.interfaces.pojo.stock.quotation.PositionStock;
import com.waben.stock.interfaces.pojo.stock.quotation.StockMarket;
import com.waben.stock.risk.warpper.ApplicationContextBeanFactory;
import com.waben.stock.risk.warpper.messagequeue.rabbitmq.PositionSellOutProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

public class RiskProcess implements Callable<List<PositionStock>> {
    Logger logger = LoggerFactory.getLogger(getClass());
    private PositionSellOutProducer positionProducer = ApplicationContextBeanFactory.getBean(PositionSellOutProducer
            .class);
    private StockMarket stockMarket;
    private List<PositionStock> positionStock;

    public RiskProcess(StockMarket stockMarket, List<PositionStock> positionStocks) {
        this.stockMarket = stockMarket;
        this.positionStock = positionStocks;
    }

    @Override
    public List<PositionStock> call() {
        List<PositionStock> counts = new ArrayList<>();
        logger.info("股票:{},已持仓中订单数量:{}", stockMarket.getName(), positionStock.size());
        long start = System.currentTimeMillis();
        for (PositionStock riskBuyInStock : positionStock) {
            // 遍历容器内持仓中的点买交易订单
            // 判断  最新行情价格与 当前持仓订单买入价格   是否达到止盈或止损点位  若 达到则 执行强制卖出  卖出跌停价
            BigDecimal lastPrice = stockMarket.getLastPrice();//最新行情价格
            BigDecimal lossPosition = riskBuyInStock.getLossPosition();
            BigDecimal profitPosition = riskBuyInStock.getProfitPosition();
            logger.info("最新行情价格:{},止损价格：{},止盈价格:{}", lastPrice,lossPosition,profitPosition);
            //交易日
            Calendar date = Calendar.getInstance();
            date.setTime(riskBuyInStock.getBuyingTime());
            date.add(5, -1);
            Long expriessDay = date.getTimeInMillis() / (1000 * 60 * 60 * 24);
            //是否递延
            Boolean isDeferred = riskBuyInStock.getDeferred();
            //当前时间
            Long currentDay = new Date().getTime() / (1000 * 60 * 60 * 24);
            if (profitPosition.compareTo(lastPrice) == -1 || lossPosition.compareTo(lastPrice) == 1) {
                counts.add(riskBuyInStock);
                positionProducer.riskPositionSellOut(riskBuyInStock);
            } else {
                //如果没有递延费，达到交易日后，强制卖出
                if (currentDay == expriessDay) {
                    if (!isDeferred) {
                        counts.add(riskBuyInStock);
                        positionProducer.riskPositionSellOut(riskBuyInStock);
                    } else {
                        //进行递延费扣除,递延成功，修改股票持仓到期时间
                        Boolean result = false;
                        if (result == false) {
                            //result==false 余额不足以支付递延费
                            counts.add(riskBuyInStock);
                            positionProducer.riskPositionSellOut(riskBuyInStock);
                        }
                    }
                }
            }
        }
        long end = System.currentTimeMillis();
        logger.info("Call执行时间：{}", (end - start));
        return counts;
    }
}
