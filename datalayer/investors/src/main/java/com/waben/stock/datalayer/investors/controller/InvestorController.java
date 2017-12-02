package com.waben.stock.datalayer.investors.controller;

import com.waben.stock.datalayer.investors.business.BuyRecordBusiness;
import com.waben.stock.datalayer.investors.entity.Investor;
import com.waben.stock.datalayer.investors.service.InvestorService;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.dto.investor.InvestorDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.InvestorQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.stock.stockjy.SecuritiesStockEntrust;
import com.waben.stock.interfaces.service.inverstors.InvestorInterface;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.util.PageToPageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author Created by yuyidi on 2017/11/5.
 * @desc
 */
@RestController
@RequestMapping("/investor")
public class InvestorController implements InvestorInterface {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private InvestorService investorService;
    @Autowired
    private BuyRecordBusiness buyRecordBusiness;

    /**
     * 投资人列表
     */
    public Response<PageInfo<InvestorDto>> pagesByQuery(@RequestBody InvestorQuery investorQuery) {
        Page<Investor> page = investorService.pagesByQuery(investorQuery);
        PageInfo<InvestorDto> result = PageToPageInfo.pageToPageInfo(page, InvestorDto.class);
        return new Response<>(result);
    }

    public Response<InvestorDto> fetchByUserName(@PathVariable String userName) {
        Investor investor = investorService.findByUserName(userName);
        InvestorDto investorDto = CopyBeanUtils.copyBeanProperties(investor, new InvestorDto(), false);
        return new Response<>(investorDto);
    }

    /**
     * 投资人点买记录委托下单
     *
     * @param investor     投资人ID
     * @param securitiesStockEntrust 点买记录数据传输对象
     * @param tradeSession 投资人证券商户交易session
     * @return
     */
    public Response<BuyRecordDto> stockBuyIn(@PathVariable Long investor, @RequestBody SecuritiesStockEntrust
            securitiesStockEntrust, String tradeSession) {
        Investor result = investorService.findById(investor);
        String entrustNo = investorService.buyRecordEntrust(result, securitiesStockEntrust, tradeSession);
        BuyRecordDto buyRecordDtoResponse = buyRecordBusiness.entrust(result, securitiesStockEntrust, entrustNo);
        return new Response<>(buyRecordDtoResponse);
    }

}
