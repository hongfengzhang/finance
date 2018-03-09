package com.waben.stock.applayer.operation.business;

import com.waben.stock.applayer.operation.service.stockoption.OfflineStockOptionTradeService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.stockoption.OfflineStockOptionTradeDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OfflineStockOptionTradeBusiness {
    @Autowired
    @Qualifier("offlinestockoptiontradeFeignService")
    private OfflineStockOptionTradeService offlineStockOptionTradeService;

    public OfflineStockOptionTradeDto add(OfflineStockOptionTradeDto offlineStockOptionTradeDto) {
        Response<OfflineStockOptionTradeDto> response = offlineStockOptionTradeService.add(offlineStockOptionTradeDto);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        } else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public OfflineStockOptionTradeDto settlement(Long id, BigDecimal sellingPrice) {
        Response<OfflineStockOptionTradeDto> response = offlineStockOptionTradeService.settlement(id, sellingPrice);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        } else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public OfflineStockOptionTradeDto find(Long id) {
        Response<OfflineStockOptionTradeDto> response = offlineStockOptionTradeService.find(id);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        } else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

}
