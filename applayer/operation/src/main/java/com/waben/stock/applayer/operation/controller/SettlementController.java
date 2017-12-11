package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.SettlementBusiness;
import com.waben.stock.interfaces.dto.buyrecord.SettlementDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.SettlementQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/settlement")
public class SettlementController {

    @Autowired
    private SettlementBusiness settlementBusiness;

    @GetMapping("/index")
    public String index() {
        return "strategy/holding/index";
    }

    @GetMapping("/pages")
    @ResponseBody
    public Response<PageInfo<SettlementDto>> pages(SettlementQuery settlementQuery) {
        PageInfo<SettlementDto> response = settlementBusiness.pages(settlementQuery);
        return new Response<>(response);
    }
}
