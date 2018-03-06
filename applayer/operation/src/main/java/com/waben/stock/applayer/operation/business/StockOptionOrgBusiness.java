package com.waben.stock.applayer.operation.business;

import com.waben.stock.applayer.operation.service.stockoption.StockOptionOrgService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.stockcontent.StockExponentDto;
import com.waben.stock.interfaces.dto.stockoption.StockOptionOrgDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockOptionOrgBusiness {
    @Autowired
    @Qualifier("stockoptionorgFeignService")
    private StockOptionOrgService stockOptionOrgService;


    public List<StockOptionOrgDto> fetchStockOptionOrgs() {
        Response<List<StockOptionOrgDto>> response = stockOptionOrgService.lists();
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        } else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

}
