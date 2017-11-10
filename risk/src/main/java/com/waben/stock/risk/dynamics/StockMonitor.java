package com.waben.stock.risk.dynamics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author Created by yuyidi on 2017/11/6.
 * @desc 股票行情监控
 */
@Component
@RefreshScope
public class StockMonitor implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${info.comment:error}")
    private String show;

    public void run(String... strings) throws Exception {
        logger.info("初始化：{}", show);
    }
}
