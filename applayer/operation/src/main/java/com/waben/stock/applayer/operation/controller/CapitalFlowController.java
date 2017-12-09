package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.CapitalFlowBusiness;
import com.waben.stock.interfaces.dto.publisher.CapitalFlowDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.CapitalFlowQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/capitalflow")
public class CapitalFlowController {

    @Autowired
    private CapitalFlowBusiness  capitalFlowBusiness;

    @RequestMapping("/index")
    public String index() {
        return "publisher/account/index";
    }

    @GetMapping("/pages")
    @ResponseBody
    public Response<PageInfo<CapitalFlowDto>> pages(CapitalFlowQuery capitalFlowQuery) {
        PageInfo<CapitalFlowDto> response = capitalFlowBusiness.pages(capitalFlowQuery);
        return new Response<>(response);
    }
}
