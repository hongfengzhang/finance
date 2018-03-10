package com.waben.stock.risk.business;


import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.stockoption.StockOptionTradeDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.risk.service.StockOptionTradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockOptionTradeBusiness {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    @Qualifier("stockoptiontradeFeignService")
    private StockOptionTradeService stockOptionTradeService;

    public List<StockOptionTradeDto> stockOptionsWithTurnover() {
        Response<List<StockOptionTradeDto>> response = stockOptionTradeService.stockOptionsWithState(3);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        } else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }


    public StockOptionTradeDto stockOptionDueTreatment(Long id) {
        Response<StockOptionTradeDto> response = stockOptionTradeService.exercise(id);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        } else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

}
