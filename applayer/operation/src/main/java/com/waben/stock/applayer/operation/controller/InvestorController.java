package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.InvestorBusiness;
import com.waben.stock.interfaces.dto.investor.InvestorDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.InvestorQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Created by yuyidi on 2017/11/29.
 * @desc
 */
@Controller
@RequestMapping("/investor")
public class InvestorController {

    @Autowired
    private InvestorBusiness investorBusiness;

    @RequestMapping("/index")
    public String index() {
        return "manage/investor/investor";
    }

    @GetMapping("/pages")
    @ResponseBody
    public Response<PageInfo<InvestorDto>> pages(InvestorQuery investorQuery) {
        return new Response<>(investorBusiness.investors(investorQuery));
    }

}
