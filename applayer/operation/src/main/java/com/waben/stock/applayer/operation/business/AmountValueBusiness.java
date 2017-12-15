package com.waben.stock.applayer.operation.business;

import com.waben.stock.applayer.operation.service.stock.AmountValueService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.stockcontent.AmountValueDto;
import com.waben.stock.interfaces.dto.stockcontent.LossDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.AmountValueQuery;
import com.waben.stock.interfaces.pojo.query.LossQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AmountValueBusiness {

    @Autowired
    @Qualifier("amountvalueFeignService")
    private AmountValueService amountValueService;

    public PageInfo<AmountValueDto> pages(AmountValueQuery query) {
        Response<PageInfo<AmountValueDto>> response = amountValueService.pagesByQuery(query);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }
}
