package com.waben.stock.datalayer.investors.container;

import com.waben.stock.interfaces.pojo.stock.SecuritiesStockEntrust;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Created by yuyidi on 2017/12/3.
 * @desc 券商股票申请委托买入容器
 */
@Component
public class StockApplyEntrustSellOutContainer {

    Logger logger = LoggerFactory.getLogger(getClass());
    Map<Long, SecuritiesStockEntrust> sellOutContainer = new ConcurrentHashMap<>();

    public void add(SecuritiesStockEntrust stock) {
        sellOutContainer.put(stock.getBuyRecordId(), stock);
    }

    public void remove(Long buyrecordId) {
        sellOutContainer.remove(buyrecordId);
    }

    public Map<Long, SecuritiesStockEntrust> getSellOutContainer() {
        return sellOutContainer;
    }

}
