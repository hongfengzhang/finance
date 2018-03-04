package com.waben.stock.applayer.operation.business;

import com.waben.stock.applayer.operation.service.stockoption.StockOptionTradeService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.stockoption.StockOptionTradeDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StockOptionTradeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockOptionTradeBusiness {
    @Autowired
    private StockOptionTradeService stockOptionTradeService;

    public PageInfo<StockOptionTradeDto> pages(StockOptionTradeQuery query) {
        Response<PageInfo<StockOptionTradeDto>> response = stockOptionTradeService.pagesByQuery(query);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }
}
