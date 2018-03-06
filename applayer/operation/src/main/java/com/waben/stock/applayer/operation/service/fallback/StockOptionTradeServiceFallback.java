package com.waben.stock.applayer.operation.service.fallback;

import com.waben.stock.applayer.operation.service.stockoption.StockOptionTradeService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.stockoption.StockOptionTradeDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StockOptionTradeQuery;
import com.waben.stock.interfaces.pojo.query.StockOptionTradeUserQuery;
import org.springframework.stereotype.Component;

@Component
public class StockOptionTradeServiceFallback implements StockOptionTradeService{
    @Override
    public Response<StockOptionTradeDto> add(StockOptionTradeDto stockOptionTradeDto) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<PageInfo<StockOptionTradeDto>> pagesByUserQuery(StockOptionTradeUserQuery query) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<PageInfo<StockOptionTradeDto>> pagesByQuery(StockOptionTradeQuery query) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<StockOptionTradeDto> fetchById(Long id) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<StockOptionTradeDto> settlement(Long id) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

	@Override
	public Response<StockOptionTradeDto> userRight(Long publisherId, Long id) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}
}
