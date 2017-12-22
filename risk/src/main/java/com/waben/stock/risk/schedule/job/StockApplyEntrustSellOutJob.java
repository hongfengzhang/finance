package com.waben.stock.risk.schedule.job;

import com.waben.stock.interfaces.enums.EntrustState;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.stock.SecuritiesStockEntrust;
import com.waben.stock.interfaces.pojo.stock.stockjy.data.StockEntrustQueryResult;
import com.waben.stock.risk.container.StockApplyEntrustSellOutContainer;
import com.waben.stock.risk.warpper.messagequeue.rabbitmq.EntrustProducer;
import com.waben.stock.risk.web.SecuritiesEntrustHttp;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Created by yuyidi on 2017/12/17.
 * @desc
 */
@Component
public class StockApplyEntrustSellOutJob implements InterruptableJob {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private StockApplyEntrustSellOutContainer stockApplyEntrustSellOutContainer;
    @Autowired
    private SecuritiesEntrustHttp securitiesEntrust;
    @Autowired
    private EntrustProducer entrustProducer;

    private Boolean interrupted = false;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String tradeSession = null;
        while (!interrupted) {
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
                    if (stockEntrustQueryResult.getEntrustStatus().equals(EntrustState.HASBEENSUCCESS
                            .getIndex())) {
                        logger.info("交易委托单已交易成功，删除容器中交易单号为:{},委托单号为:{}", securitiesStockEntrust.getTradeNo(),
                                securitiesStockEntrust.getEntrustNumber());
                        // 若执行结果为true 代表订单状态已成功，则  删除集合中的数据
                        //发送给队列处理，提高委托单轮询处理速度
                        logger.info("委托订单卖出已完成:{}", entry.getKey());
                        entrustProducer.entrustSellOut(entry.getValue());
                        stockEntrusts.remove(entry.getKey());
                    }
                } catch (ServiceException ex) {
                    logger.error("券商委托单查询异常:{}", ex.getMessage());
                }
            }
        }
    }

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        logger.info("股票申请委托卖出任务被中断");
        interrupted = true;
    }
}
