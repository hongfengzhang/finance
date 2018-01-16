package com.waben.stock.risk.schedule.job;

import com.waben.stock.interfaces.pojo.stock.SecuritiesStockEntrust;
import com.waben.stock.interfaces.pojo.stock.quotation.PositionStock;
import com.waben.stock.interfaces.pojo.stock.quotation.StockMarket;
import com.waben.stock.risk.container.PositionStockContainer;
import com.waben.stock.risk.schedule.thread.RiskProcess;
import com.waben.stock.risk.warpper.ApplicationContextBeanFactory;
import com.waben.stock.risk.web.StockQuotationHttp;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Created by yuyidi on 2017/11/28.
 * @desc
 */
//@Component
public class StockQuotationJob implements InterruptableJob {

    Logger logger = LoggerFactory.getLogger(getClass());

    private PositionStockContainer positionStockContainer = ApplicationContextBeanFactory.getBean
            (PositionStockContainer.class);

    private StockQuotationHttp stockQuotationHttp = ApplicationContextBeanFactory.getBean(StockQuotationHttp.class);

    private ExecutorService executors = Executors.newFixedThreadPool(4);
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("开始执行行情数据拉取");
        Map<String, List<PositionStock>> riskStockContainer = positionStockContainer.getRiskStockContainer();

        Set<String> codes = riskStockContainer.keySet();
        List<String> codePrams = new ArrayList();
        codePrams.addAll(codes);
        List<StockMarket> quotations = stockQuotationHttp.fetQuotationByCode(codePrams);
        //线程处理
        for(StockMarket stockMarket: quotations) {
            List<PositionStock> stocks = riskStockContainer.get(stockMarket.getInstrumentId());
            Future<List<PositionStock>> future = executors.submit(new RiskProcess(stockMarket,stocks ));
            try {
                List<PositionStock> result = future.get();
                for (PositionStock stock : result) {
                    stocks.remove(stock);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    public void interrupt() throws UnableToInterruptJobException {
        logger.info("行情数据拉取中断");
    }

}
