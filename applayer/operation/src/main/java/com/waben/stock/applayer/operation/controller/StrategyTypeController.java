package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.StrategyTypeBusiness;
import com.waben.stock.interfaces.dto.stockcontent.StrategyTypeDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StrategyTypeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Created by yuyidi on 2017/12/6.
 * @desc
 */
@Controller
@RequestMapping("/strategytype")
public class StrategyTypeController {

    @Autowired
    private StrategyTypeBusiness strategyTypeBusiness;

    @RequestMapping("/index")
    public String stock() {
        return "stock/strategytype/index";
    }

    @GetMapping("/pages")
    @ResponseBody
    public Response<PageInfo<StrategyTypeDto>> pages(StrategyTypeQuery strategyTypeQuery) {
        PageInfo<StrategyTypeDto> response = strategyTypeBusiness.pages(strategyTypeQuery);
        return new Response<>(response);
    }
}
