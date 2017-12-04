package com.waben.stock.risk.init;

import com.netflix.discovery.converters.Auto;
import com.waben.stock.interfaces.enums.EntrustState;
import com.waben.stock.interfaces.pojo.stock.stockjy.SecuritiesStockEntrust;
import com.waben.stock.risk.container.SecuritiesStockEntrustContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Created by yuyidi on 2017/12/4.
 * @desc
 */
@Component
public class StockEntrustLoopProcessor implements CommandLineRunner {
    Logger logger = LoggerFactory.getLogger(getClass());
    Executor executor = Executors.newFixedThreadPool(2);
    @Autowired
    private SecuritiesStockEntrustContainer securitiesStockEntrustContainer;

    @Override
    public void run(String... args) throws Exception {
        Thread task = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    List<SecuritiesStockEntrust> stockEntrusts = securitiesStockEntrustContainer.queryEntrust();
                    for (SecuritiesStockEntrust securitiesStockEntrust : stockEntrusts) {
                        logger.info("此处执行http，当前委托订单为：{}",securitiesStockEntrust.getTradeNo());
                        if (securitiesStockEntrust.getEntrustState().equals(EntrustState.HASBEENSUCCESS)) {
                            // 若执行结果为true 代表订单状态已成功，则  删除集合中的数据
                            securitiesStockEntrustContainer.remove(securitiesStockEntrust);
                        }
                    }
                }
            }
        });
        task.start();
    }
}
