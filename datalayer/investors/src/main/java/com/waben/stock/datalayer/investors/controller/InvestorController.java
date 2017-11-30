package com.waben.stock.datalayer.investors.controller;

import com.waben.stock.datalayer.investors.entity.Investor;
import com.waben.stock.datalayer.investors.service.InvestorService;
import com.waben.stock.interfaces.dto.investor.InvestorDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.InvestorQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.service.inverstors.InvestorInterface;
import com.waben.stock.interfaces.util.PageToPageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    /**
     * 投资人列表
     */
    public Response<PageInfo<InvestorDto>> pagesByQuery(@RequestBody InvestorQuery investorQuery) {
        Page<Investor> page = investorService.pagesByQuery(investorQuery);
        PageInfo<InvestorDto> result = PageToPageInfo.pageToPageInfo(page, InvestorDto.class);
        return new Response<>(result);
    }

}
