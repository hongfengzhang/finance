package com.waben.stock.applayer.operation.business;

import com.waben.stock.applayer.operation.service.investor.SecurityAccountService;
import com.waben.stock.interfaces.dto.investor.InvestorDto;
import com.waben.stock.interfaces.dto.investor.SecurityAccountDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.InvestorQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.SecurityAccountQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Created by yuyidi on 2017/11/30.
 * @desc
 */
@Service
public class SecurityAccountBusiness {

    @Autowired
    private SecurityAccountService securityAccountService;

    public PageInfo<SecurityAccountDto> securityAccounts(SecurityAccountQuery securityAccountQuery) {
        Response<PageInfo<SecurityAccountDto>> response = securityAccountService.pagesByQuery(securityAccountQuery);
        if (response.getCode().equals("200")) {
            return response.getResult();
        }
        throw new ServiceException(response.getCode());
    }
}
