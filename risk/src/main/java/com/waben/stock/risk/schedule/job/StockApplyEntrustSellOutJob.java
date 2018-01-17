package com.waben.stock.risk.schedule.job;

import com.waben.stock.interfaces.enums.EntrustState;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.stock.SecuritiesStockEntrust;
import com.waben.stock.interfaces.pojo.stock.stockjy.data.StockEntrustQueryResult;
import com.waben.stock.risk.container.StockApplyEntrustSellOutContainer;
import com.waben.stock.risk.warpper.ApplicationContextBeanFactory;
import com.waben.stock.risk.warpper.messagequeue.rabbitmq.EntrustProducer;
import com.waben.stock.risk.web.SecuritiesEntrustHttp;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author Created by yuyidi on 2017/12/17.
 * @desc
 */
//@Component
public class StockApplyEntrustSellOutJob implements InterruptableJob {

    Logger logger = LoggerFactory.getLogger(getClass());

    private StockApplyEntrustSellOutContainer stockApplyEntrustSellOutContainer = ApplicationContextBeanFactory.getBean
            (StockApplyEntrustSellOutContainer.class);
    private SecuritiesEntrustHttp securitiesEntrust = ApplicationContextBeanFactory.getBean(SecuritiesEntrustHttp
            .class);
    private EntrustProducer entrustProducer = ApplicationContextBeanFactory.getBean(EntrustProducer.class);

    private Boolean interrupted = false;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("券商股票委托容器对象:{},当前对象{}", stockApplyEntrustSellOutContainer,this);
        String tradeSession = null;
        while (!interrupted) {
            try {
                logger.info("3秒后开始轮询");
                Thread.sleep(3 * 1000);
                Map<String, SecuritiesStockEntrust> stockEntrusts = stockApplyEntrustSellOutContainer
                        .getSellOutContainer();
                logger.info("券商委托股票容器内剩余:{}个委托卖出订单", stockEntrusts.size());
                for (Map.Entry<String, SecuritiesStockEntrust> entry : stockEntrusts.entrySet()) {
                    logger.info("此处执行HTTP，当前委托订单为：{}", entry.getKey());
                    try {
                        SecuritiesStockEntrust securitiesStockEntrust = entry.getValue();
                        String currTradeSession = securitiesStockEntrust.getTradeSession();
                        if (currTradeSession == null) {
                            securitiesStockEntrust.setTradeSession(tradeSession);
                            continue;
                        } else {
                            tradeSession = currTradeSession;
                        }
                        StockEntrustQueryResult stockEntrustQueryResult = securitiesEntrust.queryEntrust
                                (securitiesStockEntrust.getTradeSession(), securitiesStockEntrust
                                        .getEntrustNo());
                        if (stockEntrustQueryResult == null) {
                            stockEntrusts.remove(entry.getKey());
                            continue;
                        }
                        logger.info("委托结果：{}", EntrustState.getByIndex(stockEntrustQueryResult.getEntrustStatus()));
                        if (stockEntrustQueryResult.getEntrustStatus().equals(EntrustState.WASTEORDER.getIndex())) {
                            //废单
                            logger.info("废单:{}",entry.getKey());
                            stockEntrusts.remove(entry.getKey());
                            continue;
                        }
                        if (stockEntrustQueryResult.getEntrustStatus().equals(EntrustState.HASBEENSUCCESS
                                .getIndex())) {
                            // 若执行结果为true 代表订单状态已成功，则  删除集合中的数据
                            //发送给队列处理，提高委托单轮询处理速度
                            logger.info("委托订单已完成:{}", entry.getKey());
                            //交易委托单委托成功之后，委托价格变成成交价格，委托数量变成成交数量
                            Float amount = Float.valueOf(stockEntrustQueryResult.getEntrustAmount());
                            securitiesStockEntrust.setEntrustNumber(amount.intValue());
                            securitiesStockEntrust.setEntrustPrice(new BigDecimal(stockEntrustQueryResult.getBusinessPrice()));
                            entrustProducer.entrustSellOut(entry.getValue());
                            stockEntrusts.remove(entry.getKey());
                            logger.info("交易委托单已交易成功，删除容器中交易单号为:{},委托数量为:{},委托价格:{}", securitiesStockEntrust.getTradeNo(),
                                    securitiesStockEntrust.getEntrustNumber(),securitiesStockEntrust.getEntrustPrice());
                        }
                    } catch (ServiceException ex) {
                        logger.error("券商委托单查询异常:{}", ex.getMessage());
                    }
                }
            } catch (InterruptedException e) {
                logger.info("中断异常:{}", e);
                e.printStackTrace();
            }catch (Exception e) {
                logger.info("轮询异常：{}", e.getMessage());
            }
        }
    }

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        logger.info("股票申请委托卖出任务被中断");
        interrupted = true;
    }
}
