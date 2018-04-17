package com.waben.stock.risk.service.fallback;

import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.stockoption.StockOptionTradeDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StockOptionTradeQuery;
import com.waben.stock.interfaces.pojo.query.StockOptionTradeUserQuery;
import com.waben.stock.risk.service.StockOptionTradeService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StockOptionTradeServiceFallback implements StockOptionTradeService {
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
	
    @Override
    public Response<StockOptionTradeDto> success(Long id) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<StockOptionTradeDto> exercise(Long id) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
	public Response<StockOptionTradeDto> fail(Long id) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

    @Override
    public Response<List<StockOptionTradeDto>> stockOptionsWithState(Integer state) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<StockOptionTradeDto> dueTreatmentExercise(Long id) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
	public Response<StockOptionTradeDto> modify(Long id) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

}
