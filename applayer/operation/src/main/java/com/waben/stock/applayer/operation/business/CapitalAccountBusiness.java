package com.waben.stock.applayer.operation.business;

import com.waben.stock.applayer.operation.service.publisher.CapitalAccountService;
import com.waben.stock.interfaces.dto.publisher.CapitalAccountDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.CapitalAccountQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CapitalAccountBusiness {

    @Autowired
    @Qualifier("capitalAccountFeignService")
    private CapitalAccountService capitalAccountService;

    public PageInfo<CapitalAccountDto> pages(CapitalAccountQuery query) {
        Response<PageInfo<CapitalAccountDto>> response = capitalAccountService.pages(query);
        if ("200".equals(response.getCode())) {
            return response.getResult();
        }
        throw new ServiceException(response.getCode());
    }
}
