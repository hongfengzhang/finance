package com.waben.stock.datalayer.publisher.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waben.stock.datalayer.publisher.entity.FavoriteStock;
import com.waben.stock.datalayer.publisher.repository.FavoriteStockDao;
import com.waben.stock.interfaces.constants.ExceptionConstant;
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

	public FavoriteStock save(Long publisherId, Long stockId, String stockName, String stockCode) {
		FavoriteStock favorite = favoriteStockDao.retrive(publisherId, stockCode);
		if (favorite != null) {
			throw new ServiceException(ExceptionConstant.STOCK_ALREADY_FAVORITE_EXCEPTION);
		}
		favorite = new FavoriteStock();
		favorite.setCode(stockCode);
		favorite.setFavoriteTime(new Date());
		favorite.setName(stockName);
		favorite.setPublisherId(publisherId);
		favorite.setStockId(stockId);
		Integer maxSort = favoriteStockDao.retriveMaxSort(publisherId);
		favorite.setSort(maxSort != null ? maxSort + 1 : 1);
		favoriteStockDao.create(favorite);
		return favorite;
	}

	@Transactional
	public void remove(Long publisherId, String[] stockCodeArr) {
		favoriteStockDao.delete(publisherId, stockCodeArr);
	}

	public List<FavoriteStock> list(Long publisherId) {
		return favoriteStockDao.list(publisherId);
	}

	public void top(Long publisherId, String[] stockCodeArr) {
		if (stockCodeArr != null && stockCodeArr.length > 0) {
			List<FavoriteStock> others = favoriteStockDao.listByCodeNotIn(publisherId, stockCodeArr);
			int topSize = 0;
			for (String stockCode : stockCodeArr) {
				FavoriteStock favoriteStock = favoriteStockDao.retrive(publisherId, stockCode);
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

	public List<String> listStockCodeByPublisherId(Long publisherId) {
		return favoriteStockDao.listStockCode(publisherId);
	}

}
