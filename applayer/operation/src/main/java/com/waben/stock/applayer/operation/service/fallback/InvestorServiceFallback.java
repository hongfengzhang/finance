package com.waben.stock.applayer.operation.service.fallback;

import com.waben.stock.applayer.operation.service.investor.InvestorService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.dto.investor.InvestorDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.InvestorQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.stock.SecuritiesStockEntrust;
import org.springframework.stereotype.Component;

/**
 * @author Created by yuyidi on 2017/11/30.
 * @desc
 */
@Component
public class InvestorServiceFallback implements InvestorService {

    @Override
    public Response<PageInfo<InvestorDto>> pagesByQuery(InvestorQuery investorQuery) {
        throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<InvestorDto> fetchByUserName(String userName) {
        throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<BuyRecordDto> stockApplyBuyIn(Long investor, SecuritiesStockEntrust securitiesStockEntrust,
                                                  String tradeSession) {
        throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<BuyRecordDto> stockApplySellOut(Long investor, SecuritiesStockEntrust securitiesStockEntrust, String tradeSession) {
        throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }
}
