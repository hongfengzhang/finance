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
public class StockMonitor implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(getClass());


    /***
    * @author yuyidi 2017-11-27 20:04:24
    * @method run
     * @param strings
    * @return void
    * @description 系统启动完成，初始化行情接口
    */
    public void run(String... strings) throws Exception {

    }
}
