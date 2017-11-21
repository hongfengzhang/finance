package com.waben.stock.datalayer.stockcontent.service;

import com.waben.stock.datalayer.stockcontent.repository.StockDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 股票 Service
 *
 * @author luomengan
 */
@Service
public class StockService {

    @Autowired
    private StockDao stockDao;



}
