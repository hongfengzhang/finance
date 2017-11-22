package com.waben.stock.datalayer.stockcontent.service;

import com.waben.stock.datalayer.stockcontent.entity.StockExponent;
import com.waben.stock.datalayer.stockcontent.repository.StockExponentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Created by yuyidi on 2017/11/21.
 * @desc
 */
@Service
public class StockExponentService {

    @Autowired
    private StockExponentDao stockExponentDao;

    public List<StockExponent> findStockExponts() {
        return stockExponentDao.list();
    }

    public StockExponent findStockExponent(String exponeneCode) {
        return stockExponentDao.retrieveWithExponeneCode(exponeneCode);
    }
}
