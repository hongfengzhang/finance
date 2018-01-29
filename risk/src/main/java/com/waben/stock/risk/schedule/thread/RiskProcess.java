package com.waben.stock.risk.schedule.thread;

import com.waben.stock.interfaces.enums.WindControlType;
import com.waben.stock.interfaces.pojo.stock.quotation.PositionStock;
import com.waben.stock.interfaces.pojo.stock.quotation.StockMarket;
import javafx.geometry.Pos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class RiskProcess implements Callable<List<PositionStock>> {
    public static final DateFormat df = DateFormat.getTimeInstance();
    Logger logger = LoggerFactory.getLogger(getClass());

    private StockMarket stockMarket;
    private List<PositionStock> positionStock;
    private Map<String,PositionStock> entrustSellOutContainer;
    private long millisOfDay = 1000 * 60 * 60 * 24;

    public RiskProcess(StockMarket stockMarket, List<PositionStock> positionStocks,Map<String,PositionStock> entrustSellOutContainer) {
        this.stockMarket = stockMarket;
        this.positionStock = positionStocks;
        this.entrustSellOutContainer = entrustSellOutContainer;
    }

    @Override
    public List<PositionStock> call() {
        List<PositionStock> counts = new ArrayList<>();
        logger.info("股票:{},已持仓中订单数量:{}", stockMarket.getName(), positionStock.size());
        long start = System.currentTimeMillis();
        String tradeSession = "880003450508";
        // 遍历容器内持仓中的点买交易订单
        for (PositionStock riskBuyInStock : positionStock) {
            //如果申请卖出容器里没有该订单，说明该订单已经被申请卖出，删除持仓容器里的订单
            if(entrustSellOutContainer.get(riskBuyInStock.getTradeNo())==null){
                logger.info("删除持仓容器的数据:{}",riskBuyInStock.toString());
                counts.add(riskBuyInStock);
                continue;
            }
            String currTradeSession = riskBuyInStock.getTradeSession();
            if (currTradeSession == null) {
                logger.info("数据库中加载的持仓点买交易记录");
                if (tradeSession == null) {
                    continue;
                }
                riskBuyInStock.setTradeSession(tradeSession);
            } else {
                logger.info("最新持仓点买交易记录session:{}", currTradeSession);
                tradeSession = currTradeSession;
            }
            logger.info("当前券商session:{}", tradeSession);
            BigDecimal lastPrice = stockMarket.getLastPrice();
            BigDecimal lossPosition = riskBuyInStock.getLossPosition();
            BigDecimal profitPosition = riskBuyInStock.getProfitPosition();
            logger.info("最新行情价格:{},止损价格：{},止盈价格:{}", lastPrice, lossPosition, profitPosition);
            //过期时间
//            long expriessDay = riskBuyInStock.getExpireTime().getTime() / millisOfDay;
//            //当前时间
//
//            long currentDay = date.getTime() / millisOfDay;
            Date date = new Date();
            DateFormat df = DateFormat.getTimeInstance();//只显示出时分秒
            SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
            String format = df.format(date);
            Date currentTime = null;
            Date expriessTime = null;
            try {
                currentTime = sdf.parse(format);
                expriessTime = sdf.parse("10:00:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy-MM-dd");
            String currentDay = sdfDate.format(date);
            String expriessDay = sdfDate.format(riskBuyInStock.getExpireTime());
            logger.info("过期时间:{},当前时间:{}", expriessDay, currentDay);
            //判断持仓到期时间是否已经达到且是否达到14:40:00
            if (currentDay.equalsIgnoreCase(expriessDay)&&currentTime.getTime()>=expriessTime.getTime()) {
                riskBuyInStock.setWindControlType(WindControlType.TRADINGEND.getIndex());
            } else {
                // 判断  最新行情价格与 当前持仓订单买入价格   是否达到止盈或止损点位  若 达到则 执行强制卖出  卖出跌停价
                if (profitPosition.compareTo(lastPrice)==-1||profitPosition.compareTo(lastPrice)==0||lossPosition.compareTo(lastPrice)==1||lossPosition.compareTo(lastPrice)==0) {
                    if (profitPosition.compareTo(lastPrice) == -1) {
                        //达到止盈点位
                        riskBuyInStock.setWindControlType(WindControlType.REACHPROFITPOINT.getIndex());
                    } else {
                        //达到止损点位
                        riskBuyInStock.setWindControlType(WindControlType.REACHLOSSPOINT.getIndex());
                    }
                    logger.info("满足止损止盈条件:{}", riskBuyInStock.getTradeNo());
                } else {
                    logger.info("当前持仓订单未满足止损或止盈条件,不执行风控强制平仓：{}", riskBuyInStock.getTradeNo());
                    continue;
                }
            }
            //若当前时间点是在14:40之前 则不进入风控队列
            riskBuyInStock.setEntrustPrice(stockMarket.getDownLimitPrice());
            counts.add(riskBuyInStock);
        }
        long end = System.currentTimeMillis();
        logger.info("Call执行时间：{}", (end - start));
        return counts;
    }
}