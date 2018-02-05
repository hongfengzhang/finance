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
public class StockApplyEntrustBuyInContainer {

    Logger logger = LoggerFactory.getLogger(getClass());
    Map<Long, SecuritiesStockEntrust> buyInContainer = new ConcurrentHashMap<>();

    public void add(SecuritiesStockEntrust stock) {
        buyInContainer.put(stock.getBuyRecordId(), stock);
    }

    public void remove(Long buyrecordId) {
        buyInContainer.remove(buyrecordId);
    }

    public Map<Long, SecuritiesStockEntrust> getBuyInContainer() {
        return buyInContainer;
    }

}
