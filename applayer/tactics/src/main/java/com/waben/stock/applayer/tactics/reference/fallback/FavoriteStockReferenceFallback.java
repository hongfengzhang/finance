package com.waben.stock.applayer.tactics.reference.fallback;

import java.util.List;

import org.springframework.stereotype.Component;

import com.waben.stock.applayer.tactics.reference.FavoriteStockReference;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.publisher.FavoriteStockDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;

/**
 * 收藏股票 reference服务接口fallback
 *
 * @author luomengan
 */
@Component
public class FavoriteStockReferenceFallback implements FavoriteStockReference {

	@Override
	public Response<FavoriteStockDto> add(FavoriteStockDto favoriteStockDto) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<Void> drop(Long publisherId, String stockCodes) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<Void> top(Long publisherId, String stockCodes) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<List<FavoriteStockDto>> listsByPublisherId(Long publisherId) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<List<String>> listsStockCode(Long publisherId) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

}
