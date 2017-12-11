package com.waben.stock.applayer.operation.business;

import com.waben.stock.applayer.operation.service.buyrecord.BuyRecordService;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StrategyHoldingQuery;
import com.waben.stock.interfaces.pojo.query.StrategyPostedQuery;
import com.waben.stock.interfaces.pojo.query.StrategyUnwindQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class StrategyBusiness {
    @Autowired
    @Qualifier("buyRecordFeignService")
    private BuyRecordService buyRecordService;

    public PageInfo<BuyRecordDto> postedPages(StrategyPostedQuery strategyPostedQuery) {
        Response<PageInfo<BuyRecordDto>> response = buyRecordService.pagesByPostedQuery(strategyPostedQuery);
        if ("200".equals(response.getCode())) {
            return response.getResult();
        }
        throw new ServiceException(response.getCode());
    }

    public PageInfo<BuyRecordDto> holdingPages(StrategyHoldingQuery strategyHoldingQuery) {
        Response<PageInfo<BuyRecordDto>> response = buyRecordService.pagesByHoldingQuery(strategyHoldingQuery);
        if ("200".equals(response.getCode())) {
            return response.getResult();
        }
        throw new ServiceException(response.getCode());
    }


    public PageInfo<BuyRecordDto> unwindPages(StrategyUnwindQuery strategyUnwindQuery) {
        Response<PageInfo<BuyRecordDto>> response = buyRecordService.pagesByUnwindQuery(strategyUnwindQuery);
        if ("200".equals(response.getCode())) {
            return response.getResult();
        }
        throw new ServiceException(response.getCode());
    }
}
