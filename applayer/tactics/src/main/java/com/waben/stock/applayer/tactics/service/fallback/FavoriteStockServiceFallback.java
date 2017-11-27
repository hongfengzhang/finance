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
	public Response<FavoriteStockDto> add(FavoriteStockDto favoriteStockDto) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public Response<String> drop(Long publisherId, String stockIds) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public Response<String> top(Long publisherId, String stockIds) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public Response<List<FavoriteStockDto>> listsByPublisherId(Long publisherId) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public Response<List<Long>> listsStockId(Long publisherId) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

}
