package com.waben.stock.risk.container;

import com.waben.stock.interfaces.pojo.stock.SecuritiesStockEntrust;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Created by yuyidi on 2017/12/3.
 * @desc 券商股票申请委托卖出容器
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class StockApplyEntrustSellOutContainer {

    Logger logger = LoggerFactory.getLogger(getClass());
    private List<SecuritiesStockEntrust> container = new ArrayList<>(1000);

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private Lock read = readWriteLock.readLock();
    private Lock write = readWriteLock.writeLock();

    public void add(SecuritiesStockEntrust securitiesStockEntrust) {
        write.lock();
        try {
            container.add(securitiesStockEntrust);
        } finally {
            write.unlock();
        }
    }

    public void remove(SecuritiesStockEntrust securitiesStockEntrust) {
        write.lock();
        try {
            container.remove(securitiesStockEntrust);
        } finally {
            write.unlock();
        }
    }

    public List<SecuritiesStockEntrust> queryEntrust() {
        read.lock();
        try {
            return container;
        } finally {
            read.unlock();
        }
    }

}
