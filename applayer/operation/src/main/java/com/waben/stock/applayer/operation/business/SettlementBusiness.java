package com.waben.stock.applayer.operation.business;

import com.waben.stock.applayer.operation.service.buyrecord.SettlementService;
import com.waben.stock.interfaces.dto.buyrecord.SettlementDto;
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
    @Qualifier("SettlementFeignService")
    private SettlementService settlementService;

    public PageInfo<SettlementDto> pages(SettlementQuery settlementQuery) {
        Response<PageInfo<SettlementDto>> response = settlementService.pagesByQuery(settlementQuery);
        if ("200".equals(response.getCode())) {
            return response.getResult();
        }
        throw new ServiceException(response.getCode());

    }
}
