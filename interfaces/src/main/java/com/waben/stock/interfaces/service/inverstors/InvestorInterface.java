package com.waben.stock.interfaces.service.inverstors;

import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.dto.investor.InvestorDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.InvestorQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.stock.SecuritiesStockEntrust;
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

    @RequestMapping(value = "/{investor}/buyrecord/entrust", method = RequestMethod.POST, consumes = MediaType
            .APPLICATION_JSON_VALUE)
    Response<BuyRecordDto> stockBuyIn(@PathVariable("investor") Long investor, @RequestBody SecuritiesStockEntrust
            securitiesStockEntrust, @RequestParam("tradeSession") String tradeSession);
}
