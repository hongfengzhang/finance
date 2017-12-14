package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.LossBusiness;
import com.waben.stock.interfaces.dto.stockcontent.AmountValueDto;
import com.waben.stock.interfaces.dto.stockcontent.LossDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.AmountValueQuery;
import com.waben.stock.interfaces.pojo.query.LossQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/loss")
public class LossController {

    @Autowired
    private LossBusiness lossBusiness;

    @GetMapping("/index")
    public String index() {
        return "/stock/loss/index";
    }

    @GetMapping("/pages")
    @ResponseBody
    public Response<PageInfo<LossDto>> pages(LossQuery query) {
        PageInfo<LossDto> response = lossBusiness.pages(query);
        return new Response<>(response);
    }
}
