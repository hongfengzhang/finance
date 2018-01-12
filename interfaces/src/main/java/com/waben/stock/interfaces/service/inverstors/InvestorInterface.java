package com.waben.stock.interfaces.service.inverstors;

import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.dto.investor.InvestorDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.InvestorQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.stock.SecuritiesStockEntrust;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author Created by yuyidi on 2017/11/30.
 * @desc
 */
public interface InvestorInterface {

    @RequestMapping(value = "/pages", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    Response<PageInfo<InvestorDto>> pagesByQuery(@RequestBody InvestorQuery investorQuery);

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    Response<InvestorDto> fetchByUserName(@PathVariable("username") String username);

    @RequestMapping(value = "/{investor}/buyrecord/applybuyin", method = RequestMethod.POST, consumes = MediaType
            .APPLICATION_JSON_VALUE)
    Response<BuyRecordDto> stockApplyBuyIn(@PathVariable("investor") Long investor, @RequestBody SecuritiesStockEntrust
            securitiesStockEntrust, @RequestParam("tradeSession") String tradeSession);

    @RequestMapping(value = "/{investor}/buyrecord/applysellout", method = RequestMethod.POST, consumes = MediaType
            .APPLICATION_JSON_VALUE)
    Response<BuyRecordDto> stockApplySellOut(@PathVariable("investor") Long investor, @RequestBody SecuritiesStockEntrust
            securitiesStockEntrust, @RequestParam("tradeSession") String tradeSession);
    
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    Response<List<InvestorDto>> fetchAllInvestors();
}
