package com.waben.stock.applayer.operation.service.fallback;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.waben.stock.applayer.operation.service.stockoption.OfflineStockOptionTradeService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.stockoption.OfflineStockOptionTradeDto;
import com.waben.stock.interfaces.pojo.Response;

@Component
public class OfflineStockOptionTradesServiceFallback implements OfflineStockOptionTradeService{
    @Override
    public Response<OfflineStockOptionTradeDto> add(OfflineStockOptionTradeDto offlineStockOptionTradeDto) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);

    }

    @Override
    public Response<OfflineStockOptionTradeDto> find(Long id) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<OfflineStockOptionTradeDto> settlement(Long id, BigDecimal sellingPrice) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<OfflineStockOptionTradeDto> exercise(Long id) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

}
