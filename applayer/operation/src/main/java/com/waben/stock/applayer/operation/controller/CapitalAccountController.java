package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.CapitalAccountBusiness;
import com.waben.stock.interfaces.dto.publisher.CapitalAccountDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.CapitalAccountQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/capitalaccount")
public class CapitalAccountController {

    @Autowired
    private CapitalAccountBusiness captailAccountBusiness;

    @RequestMapping("/index")
    public String index() {
        return "publisher/capitalaccount/index";
    }

    @GetMapping("/pages")
    @ResponseBody
    public Response<PageInfo<CapitalAccountDto>> pages(CapitalAccountQuery capitalAccountQuery) {
        PageInfo<CapitalAccountDto> response = captailAccountBusiness.pages(capitalAccountQuery);
        return new Response<>(response);
    }
}
