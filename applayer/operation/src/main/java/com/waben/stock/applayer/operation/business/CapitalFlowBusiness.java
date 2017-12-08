package com.waben.stock.applayer.operation.business;

import com.waben.stock.applayer.operation.service.publisher.CapitalFlowService;
import com.waben.stock.interfaces.dto.publisher.CapitalFlowDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.CapitalFlowQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CapitalFlowBusiness {
    @Autowired
    @Qualifier("capitalFlowFeignService")
    private CapitalFlowService capitalFlowService;

    public PageInfo<CapitalFlowDto> pages(CapitalFlowQuery query) {
        Response<PageInfo<CapitalFlowDto>> response = capitalFlowService.pagesByQuery(query);
        if ("200".equals(response.getCode())) {
            return response.getResult();
        }
        throw new ServiceException(response.getCode());
    }
}
