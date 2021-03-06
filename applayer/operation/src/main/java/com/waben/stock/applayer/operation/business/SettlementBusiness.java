package com.waben.stock.applayer.operation.business;

import com.waben.stock.applayer.operation.service.buyrecord.SettlementService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.buyrecord.SettlementDto;
import com.waben.stock.interfaces.dto.investor.InvestorDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.SettlementQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SettlementBusiness {

    @Autowired
    @Qualifier("settlementFeignService")
    private SettlementService settlementService;

    public PageInfo<SettlementDto> pages(SettlementQuery settlementQuery) {
        Response<PageInfo<SettlementDto>> response = settlementService.pagesByQuery(settlementQuery);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());

    }

    public SettlementDto findByBuyRecord(Long id) {
        Response<SettlementDto> response = settlementService.fetchByBuyRecord(id);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }
}
