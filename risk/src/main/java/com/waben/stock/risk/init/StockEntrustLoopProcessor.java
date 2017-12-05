package com.waben.stock.risk.init;

import com.waben.stock.interfaces.enums.EntrustState;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.stock.SecuritiesStockEntrust;
import com.waben.stock.interfaces.pojo.stock.stockjy.data.StockEntrustQueryResult;
import com.waben.stock.risk.container.SecuritiesStockEntrustContainer;
import com.waben.stock.risk.web.SecuritiesEntrust;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Executor;
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
    @Autowired
    private SecuritiesEntrust securitiesEntrust;

    @Override
    public void run(String... args) throws Exception {
        Thread task = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    List<SecuritiesStockEntrust> stockEntrusts = securitiesStockEntrustContainer.queryEntrust();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    logger.info("券商委托股票容器内剩余:{}个委托订单", stockEntrusts.size());
                    for (SecuritiesStockEntrust securitiesStockEntrust : stockEntrusts) {
                        logger.info("此处执行http，当前委托订单为：{}", securitiesStockEntrust.getTradeNo());
                        try {
                            StockEntrustQueryResult stockEntrustQueryResult = securitiesEntrust.queryEntrust
                                    (securitiesStockEntrust.getTradeSession(), securitiesStockEntrust
                                            .getEntrustNumber());
                            if (stockEntrustQueryResult.getEntrustStatus().equals(EntrustState.HASBEENSUCCESS
                                    .getIndex())) {
                                logger.info("交易委托单已交易成功，删除容器中交易单号为:{},委托单号为:{}", securitiesStockEntrust.getTradeNo(),
                                        securitiesStockEntrust.getEntrustNumber());
                                // 若执行结果为true 代表订单状态已成功，则  删除集合中的数据
                                securitiesStockEntrustContainer.remove(securitiesStockEntrust);
                            }
                        } catch (ServiceException ex) {
                            logger.error("券商委托单查询异常:{}",ex.getMessage());
                        }

                    }
                }
            }
        });
        task.start();
    }
}
