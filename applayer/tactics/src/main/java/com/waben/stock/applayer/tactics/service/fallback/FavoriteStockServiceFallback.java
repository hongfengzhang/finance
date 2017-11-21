package com.waben.stock.applayer.tactics.service.fallback;

import java.util.List;

import org.springframework.stereotype.Component;

import com.waben.stock.applayer.tactics.service.FavoriteStockService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.publisher.FavoriteStockDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;

@Component
public class FavoriteStockServiceFallback implements FavoriteStockService {

	@Override
	public Response<FavoriteStockDto> addFavoriteStock(FavoriteStockDto favoriteStockDto) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public Response<String> removeFavoriteStock(String serialCode, String stockIds) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public Response<String> topFavoriteStock(String serialCode, String stockIds) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public Response<List<FavoriteStockDto>> favoriteStockList(String serialCode) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public Response<List<Long>> findStockIdByPublisherSerialCode(String serialCode) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

}
