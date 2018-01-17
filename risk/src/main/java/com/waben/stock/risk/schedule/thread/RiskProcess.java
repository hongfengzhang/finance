package com.waben.stock.risk.schedule.thread;

import com.waben.stock.interfaces.enums.WindControlType;
import com.waben.stock.interfaces.pojo.stock.SecuritiesStockEntrust;
import com.waben.stock.interfaces.pojo.stock.quotation.PositionStock;
import com.waben.stock.interfaces.pojo.stock.quotation.StockMarket;
import com.waben.stock.risk.service.BuyRecordService;
import com.waben.stock.risk.warpper.ApplicationContextBeanFactory;
import com.waben.stock.risk.warpper.messagequeue.rabbitmq.EntrustProducer;
import com.waben.stock.risk.warpper.messagequeue.rabbitmq.PositionSellOutProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

public class RiskProcess implements Callable<List<PositionStock>>{
    private PositionSellOutProducer positionProducer = ApplicationContextBeanFactory.getBean(PositionSellOutProducer.class);
    Logger logger = LoggerFactory.getLogger(getClass());
    private StockMarket stockMarket;
    private List<PositionStock> positionStock;
    @Autowired
    private BuyRecordService buyRecordService;
    public RiskProcess(StockMarket stockMarket, List<PositionStock> positionStocks) {
        this.stockMarket = stockMarket;
        this.positionStock = positionStocks;
    }

    @Override
    public List<PositionStock> call() {
        List<PositionStock> counts = new ArrayList<>();
        logger.info("ThreadCallsize:{}",positionStock.size());
        long start = System.currentTimeMillis();
        for (PositionStock riskBuyInStock : positionStock) {
            // 遍历容器内持仓中的点买交易订单
            BigDecimal lastPrice = stockMarket.getLastPrice();
            BigDecimal lossPosition = riskBuyInStock.getLossPosition();
            BigDecimal profitPosition = riskBuyInStock.getProfitPosition();
            //交易日
            Long expriessDay = riskBuyInStock.getBuyingTime().getTime()/(1000 * 60 * 60 * 24);
            //是否递延
            Boolean isDeferred = riskBuyInStock.getDeferred();
            //当前时间
            Long currentDay = new Date().getTime()/(1000 * 60 * 60 * 24);
            // 判断  最新行情价格与 当前持仓订单买入价格   是否达到止盈或止损点位  若 达到则 执行强制卖出  卖出跌停价
            if(profitPosition.compareTo(lastPrice)==-1||lossPosition.compareTo(lastPrice)==1) {
                counts.add(riskBuyInStock);
                if(profitPosition.compareTo(lastPrice)==-1) {
                    //达到止盈点位
                    riskBuyInStock.setWindControlType(WindControlType.REACHPROFITPOINT.getIndex());
                }else {
                    //达到止损点位
                    riskBuyInStock.setWindControlType(WindControlType.REACHLOSSPOINT.getIndex());
                }
                positionProducer.riskPositionSellOut(riskBuyInStock);
                if(currentDay<expriessDay) {
                    if(isDeferred) {
                        //解冻锁定递延费
                    }
                }
            }else {
                //达到交易日后，强制卖出,进行递延费扣除
                if(currentDay==expriessDay) {
                    //递延费扣除
                    buyRecordService.deferred(riskBuyInStock.getBuyRecordId());
                    counts.add(riskBuyInStock);
                    riskBuyInStock.setWindControlType(WindControlType.TRADINGEND.getIndex());
                    positionProducer.riskPositionSellOut(riskBuyInStock);
                }
            }
        }
        long end = System.currentTimeMillis();
        logger.info("Call执行时间：{}",(end-start));
        return counts;
    }
}
