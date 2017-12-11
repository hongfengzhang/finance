package com.waben.stock.applayer.operation.business;

import com.waben.stock.applayer.operation.service.stock.LossService;
import com.waben.stock.interfaces.dto.stockcontent.LossDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.LossQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class LossBusiness {

    @Autowired
    @Qualifier("lossFeignService")
    private LossService lossService;

    public PageInfo<LossDto> pages(LossQuery query) {
        Response<PageInfo<LossDto>> response = lossService.pagesByQuery(query);
        if ("200".equals(response.getCode())) {
            return response.getResult();
        }
        throw new ServiceException(response.getCode());
    }
}
