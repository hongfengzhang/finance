package com.waben.stock.datalayer.stockcontent.controller;

import com.waben.stock.datalayer.stockcontent.entity.Stock;
import com.waben.stock.datalayer.stockcontent.service.StockService;
import com.waben.stock.interfaces.dto.stockcontent.StockDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StockQuery;
import com.waben.stock.interfaces.service.stockcontent.StockInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/***
 * @author yuyidi 2017-11-22 10:08:33
 * @class com.waben.stock.datalayer.stockcontent.controller.StockController
 * @description
 */
@RestController
@RequestMapping("/stock")
public class StockController implements StockInterface {

    @Autowired
    private StockService stockService;

    @Override
    public Response<PageInfo<StockDto>> pagesByQuery(@RequestBody StockQuery staffQuery) {
        Page<Stock> stocks = stockService.stocks(staffQuery);
        PageInfo<StockDto> result = new PageInfo<>(stocks, StockDto.class);
        return new Response<>(result);
    }

}
