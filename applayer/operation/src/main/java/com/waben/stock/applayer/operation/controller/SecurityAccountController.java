package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.InvestorBusiness;
import com.waben.stock.applayer.operation.business.SecurityAccountBusiness;
import com.waben.stock.interfaces.dto.investor.InvestorDto;
import com.waben.stock.interfaces.dto.investor.SecurityAccountDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.InvestorQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.SecurityAccountQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Created by yuyidi on 2017/11/30.
 * @desc
 */
@Controller
@RequestMapping("/securityaccount")
public class SecurityAccountController {

    @Autowired
    private SecurityAccountBusiness securityAccountBusiness;

    @RequestMapping("/index")
    public String index() {
        return "investor/security/security";
    }

    @GetMapping("/pages")
    @ResponseBody
    public Response<PageInfo<SecurityAccountDto>> pages(SecurityAccountQuery securityAccountQuery) {
        return new Response<>(securityAccountBusiness.securityAccounts(securityAccountQuery));
    }

}
