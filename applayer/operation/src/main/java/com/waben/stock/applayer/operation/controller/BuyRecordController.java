package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.BuyRecordBusiness;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.dto.publisher.CapitalFlowDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.BuyRecordQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/buyrecord")
public class BuyRecordController {

    @Autowired
    private BuyRecordBusiness buyRecordBusiness;

    @GetMapping("/pages")
    @ResponseBody
    public Response<PageInfo<BuyRecordDto>> pages(BuyRecordQuery buyRecordQuery) {
        PageInfo<BuyRecordDto> response = buyRecordBusiness.pages(buyRecordQuery);
        return new Response<>(response);
    }
}
