package com.waben.stock.applayer.operation.service.fallback;

import com.waben.stock.applayer.operation.service.stockoption.StockOptionOrgService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.stockoption.StockOptionOrgDto;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StockOptionOrgServiceFallback implements StockOptionOrgService{
    @Override
    public Response<List<StockOptionOrgDto>> lists() {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }
}
