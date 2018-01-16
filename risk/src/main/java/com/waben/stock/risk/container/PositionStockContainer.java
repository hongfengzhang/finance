package com.waben.stock.risk.container;

import com.waben.stock.interfaces.pojo.stock.SecuritiesStockEntrust;
import com.waben.stock.interfaces.pojo.stock.quotation.PositionStock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Created by yuyidi on 2017/12/3.
 * @desc 持仓股票容器
 */
@Component
public class PositionStockContainer {

    Logger logger = LoggerFactory.getLogger(getClass());
    Map<String, List<PositionStock>> riskStockContainer = new ConcurrentHashMap<>();

    public void add(PositionStock stock) {
        logger.info("数据:{}",stock.toString());
        logger.info("StockCode:{}",stock.getStockCode());
        List<PositionStock> b = riskStockContainer.get(stock.getStockCode());
        if (b != null) {
            b.add(stock);
        } else {
            List<PositionStock> stocks = new CopyOnWriteArrayList<>();
            stocks.add(stock);
            riskStockContainer.put(stock.getStockCode(), stocks);
        }
        logger.info("往容器添加数据成功:{}",stock.toString());
    }

    public void removeKey(String code) {
        riskStockContainer.remove(code);
    }

    public Map<String, List<PositionStock>> getRiskStockContainer() {
        return riskStockContainer;
    }

}
