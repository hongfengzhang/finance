package com.waben.stock.applayer.operation.business;

import com.waben.stock.applayer.operation.service.investor.InvestorService;
import com.waben.stock.interfaces.dto.investor.InvestorDto;
import com.waben.stock.interfaces.dto.manage.StaffDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.InvestorQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Created by yuyidi on 2017/11/30.
 * @desc
 */
@Service
public class InvestorBusiness {

    @Autowired
    private InvestorService investorService;

    public PageInfo<InvestorDto> investors(InvestorQuery investorQuery) {
        Response<PageInfo<InvestorDto>> response = investorService.pagesByQuery(investorQuery);
        if (response.getCode().equals("200")) {
            return response.getResult();
        }
        throw new ServiceException(response.getCode());
    }
}
