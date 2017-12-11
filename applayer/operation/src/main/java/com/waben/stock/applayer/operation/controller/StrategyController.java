package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.StrategyBusiness;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/strategy")
public class StrategyController {

    @Autowired
    private StrategyBusiness strategyBusiness;

    @RequestMapping("/posted/index")
    public String strategyPosted() {
        return "strategytype/posted/index";
    }

    @RequestMapping("/holding/index")
    public String strategyHolding() {
        return "strategytype/holding/index";
    }

    @RequestMapping("/unwind/index")
    public String strategyUnwind() {
        return "strategytype/unwind/index";
    }


    @GetMapping("/posted/pages")
    @ResponseBody
    public Response<PageInfo<BuyRecordDto>> postedPages(StrategyPostedQuery strategyPostedQuery) {
        PageInfo<BuyRecordDto> response = strategyBusiness.postedPages(strategyPostedQuery);
        return new Response<>(response);
    }

    @GetMapping("/holding/pages")
    @ResponseBody
    public Response<PageInfo<BuyRecordDto>> holdingPages(StrategyHoldingQuery strategyHoldingQuery) {
        PageInfo<BuyRecordDto> response = strategyBusiness.holdingPages(strategyHoldingQuery);
        return new Response<>(response);
    }

    @GetMapping("/unwind/pages")
    @ResponseBody
    public Response<PageInfo<BuyRecordDto>> unwindPages(StrategyUnwindQuery strategyUnwindQuery) {
        PageInfo<BuyRecordDto> response = strategyBusiness.unwindPages(strategyUnwindQuery);
        return new Response<>(response);
    }

}
