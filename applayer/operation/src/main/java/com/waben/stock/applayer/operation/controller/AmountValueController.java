package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.AmountValueBusiness;
import com.waben.stock.applayer.operation.business.LossBusiness;
import com.waben.stock.interfaces.dto.manage.PermissionDto;
import com.waben.stock.interfaces.dto.stockcontent.AmountValueDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.AmountValueQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/amountvalue")
public class AmountValueController {

    @Autowired
    private AmountValueBusiness amountValueBusiness;

    @GetMapping("/index")
    public String index() {
        return "/stock/amountvalue/index";
    }

    @GetMapping("/pages")
    @ResponseBody
    public Response<PageInfo<AmountValueDto>> pages(AmountValueQuery query) {
        PageInfo<AmountValueDto> response = amountValueBusiness.pages(query);
        return new Response<>(response);
    }
}
