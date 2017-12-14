package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.BuyRecordBusiness;
import com.waben.stock.applayer.operation.business.InvestorBusiness;
import com.waben.stock.applayer.operation.util.SecurityAccount;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.dto.investor.InvestorDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.InvestorQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.stock.SecuritiesStockEntrust;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Created by yuyidi on 2017/11/29.
 * @desc
 */
@Controller
@RequestMapping("/investor")
public class InvestorController {

    @Autowired
    private InvestorBusiness investorBusiness;
    @Autowired
    private BuyRecordBusiness buyRecordBusiness;

    @RequestMapping("/index")
    public String index() {
        return "investor/manage/index";
    }

    @GetMapping("/pages")
    @ResponseBody
    public Response<PageInfo<InvestorDto>> pages(InvestorQuery investorQuery) {
        return new Response<>(investorBusiness.investors(investorQuery));
    }


    /***
     * @author yuyidi 2017-12-02 20:16:51
     * @method buyRecordEntrust
     * @param buyrecord
     * @return com.waben.stock.interfaces.pojo.Response<com.waben.stock.interfaces.pojo.stock.SecuritiesStockEntrust>
     * @description 投资点买记录买入
     */
    @RequestMapping("/buyrecord/{buyrecord}/buyin")
    @ResponseBody
    public Response<SecuritiesStockEntrust> buyRecordBuyIn(@PathVariable
                                                                   ("buyrecord") Long buyrecord) {
        InvestorDto investorDto = (InvestorDto) SecurityAccount.current().getSecurity();
        BuyRecordDto buyRecordDto = buyRecordBusiness.fetchBuyRecord(buyrecord);
        SecuritiesStockEntrust result = investorBusiness.buyIn(investorDto, buyRecordDto);
        return new Response<>(result);
    }

    @RequestMapping("/buyrecord/{buyrecord}/sellout")
    @ResponseBody
    public Response<SecuritiesStockEntrust> buyRecordSellOut(@PathVariable
                                                                     ("buyrecord") Long buyrecord) {
        InvestorDto investorDto = (InvestorDto) SecurityAccount.current().getSecurity();
        BuyRecordDto buyRecordDto = buyRecordBusiness.fetchBuyRecord(buyrecord);
        SecuritiesStockEntrust result = investorBusiness.sellOut(investorDto, buyRecordDto);
        return new Response<>(result);
    }

}
