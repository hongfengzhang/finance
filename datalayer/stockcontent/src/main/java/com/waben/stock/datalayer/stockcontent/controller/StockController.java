package com.waben.stock.datalayer.stockcontent.controller;

import com.waben.stock.datalayer.stockcontent.entity.StockExponent;
import com.waben.stock.datalayer.stockcontent.service.StockExponentService;
import com.waben.stock.datalayer.stockcontent.service.StockService;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.service.stockcontent.StockInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 股票 Controller
 *
 * @author luomengan
 */
@RestController
@RequestMapping("/stock")
public class StockController implements StockInterface {

    @Autowired
    private StockService stockService;


}
