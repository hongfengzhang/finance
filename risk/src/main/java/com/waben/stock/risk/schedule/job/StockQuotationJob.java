package com.waben.stock.risk.schedule.job;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author Created by yuyidi on 2017/11/28.
 * @desc
 */
@Component
public class StockQuotationJob implements InterruptableJob {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("开始执行行情数据拉取");
    }

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        logger.info("行情数据拉取中断");
    }
}
