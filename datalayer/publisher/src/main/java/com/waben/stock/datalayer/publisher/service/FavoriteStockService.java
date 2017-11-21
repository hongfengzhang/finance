package com.waben.stock.datalayer.publisher.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waben.stock.datalayer.publisher.entity.FavoriteStock;
import com.waben.stock.datalayer.publisher.repository.FavoriteStockDao;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.publisher.FavoriteStockDto;
import com.waben.stock.interfaces.exception.ServiceException;

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

	@Transactional
	public void removeFavoriteStock(String serialCode, Long[] stockIds) {
		favoriteStockDao.deleteBySerialCodeAndStockIdIn(serialCode, stockIds);
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

	public void topFavoriteStock(String serialCode, Long[] stockIds) {
		if (stockIds != null && stockIds.length > 0) {
			List<FavoriteStock> others = favoriteStockDao.findByStockIdNotIn(stockIds);
			int topSize = 0;
			for (Long stockId : stockIds) {
				FavoriteStock favoriteStock = favoriteStockDao.findByPublisherSerialCodeAndStockId(serialCode, stockId);
				if (favoriteStock != null) {
					favoriteStock.setSort(topSize + 1);
					favoriteStockDao.update(favoriteStock);
					topSize++;
				}
			}

			if (others != null && others.size() > 0) {
				for (FavoriteStock favoriteStock : others) {
					favoriteStock.setSort(topSize + 1);
					favoriteStockDao.update(favoriteStock);
					topSize++;
				}
			}
		}
	}

	public List<Long> findStockIdByPublisherSerialCode(String serialCode) {
		return favoriteStockDao.findStockIdByPublisherSerialCode(serialCode);
	}

}
