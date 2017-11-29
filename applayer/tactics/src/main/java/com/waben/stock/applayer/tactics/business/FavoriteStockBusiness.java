package com.waben.stock.applayer.tactics.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waben.stock.applayer.tactics.service.FavoriteStockService;
import com.waben.stock.interfaces.dto.publisher.FavoriteStockDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;

/**
 * 收藏股票 Business
 * 
 * @author luomengan
 *
 */
@Service
public class FavoriteStockBusiness {

	@Autowired
	private FavoriteStockService favoriteStockService;

	public List<Long> listsStockId(Long publisherId) {
		Response<List<Long>> response = favoriteStockService.listsStockId(publisherId);
		if (response.getCode().equals("200")) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public FavoriteStockDto save(FavoriteStockDto favoriteStockDto) {
		Response<FavoriteStockDto> response = favoriteStockService.add(favoriteStockDto);
		if (response.getCode().equals("200")) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public String remove(Long publisherId, String stockIds) {
		Response<String> response = favoriteStockService.drop(publisherId, stockIds);
		if (response.getCode().equals("200")) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public String top(Long publisherId, String stockIds) {
		Response<String> response = favoriteStockService.top(publisherId, stockIds);
		if (response.getCode().equals("200")) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public List<FavoriteStockDto> listsByPublisherId(Long publisherId) {
		Response<List<FavoriteStockDto>> response = favoriteStockService.listsByPublisherId(publisherId);
		if (response.getCode().equals("200")) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

}
