package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.StockBusiness;
import com.waben.stock.interfaces.dto.stockcontent.StockDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StockQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Created by yuyidi on 2017/11/22.
 * @desc
 */
@Controller
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockBusiness stockBusiness;

    @RequestMapping("/index")
    public String stock() {
        return "manage/stock/stock";
    }

    @GetMapping("/pages")
    @ResponseBody
    public Response<PageInfo<StockDto>> pages(StockQuery stockQuery) {
        PageInfo<StockDto> response = stockBusiness.pages(stockQuery);
        return new Response<>(response);
    }
}
