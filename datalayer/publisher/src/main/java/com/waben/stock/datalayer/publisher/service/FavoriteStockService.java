package com.waben.stock.datalayer.publisher.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waben.stock.datalayer.publisher.entity.FavoriteStock;
import com.waben.stock.datalayer.publisher.repository.FavoriteStockDao;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.FavoriteStockDto;

/**
 * 收藏股票 Service
 * 
 * @author luomengan
 *
 */
@Service
public class FavoriteStockService {

	@Autowired
	private FavoriteStockDao favoriteStockDao;

	public FavoriteStockDto addFavoriteStock(String serialCode, Long stockId, String stockName, String stockCode,
			String stockPinyinAbbr) {
		FavoriteStock check = favoriteStockDao.findByPublisherSerialCodeAndStockId(serialCode, stockId);
		if (check != null) {
			throw new ServiceException(ExceptionConstant.STOCK_ALREADY_FAVORITE_EXCEPTION);
		}
		check = new FavoriteStock();
		check.setCode(stockCode);
		check.setFavoriteTime(new Date());
		check.setName(stockName);
		check.setPinyinAbbr(stockPinyinAbbr);
		check.setPublisherSerialCode(serialCode);
		check.setStockId(stockId);
		Integer maxSort = favoriteStockDao.maxSort();
		check.setSort(maxSort != null ? maxSort + 1 : 1);
		favoriteStockDao.create(check);
		return check.copy();
	}

	public List<FavoriteStockDto> favoriteStockList(String serialCode) {
		List<FavoriteStockDto> result = new ArrayList<>();
		List<FavoriteStock> entityList = favoriteStockDao.favoriteStockList(serialCode);
		if (entityList != null && entityList.size() > 0) {
			for (FavoriteStock stock : entityList) {
				result.add(stock.copy());
			}
		}
		return result;
	}

}
