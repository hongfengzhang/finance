package com.waben.stock.applayer.operation.business;

import com.waben.stock.applayer.operation.service.publisher.CapitalAccountService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.publisher.CapitalAccountDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
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
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }
}
