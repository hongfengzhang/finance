package com.waben.stock.datalayer.publisher.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;

import com.waben.stock.datalayer.publisher.entity.FavoriteStock;
import com.waben.stock.datalayer.publisher.repository.FavoriteStockDao;
import com.waben.stock.datalayer.publisher.repository.impl.jpa.FavoriteStockRepository;

/**
 * 收藏股票 Dao实现
 * 
 * @author luomengan
 *
 */
@Repository
public class FavoriteStockDaoImpl implements FavoriteStockDao {

	@Autowired
	private FavoriteStockRepository repository;

	@Override
	public FavoriteStock create(FavoriteStock t) {
		return repository.save(t);
	}

	@Override
	public void delete(Long id) {
		repository.delete(id);
	}

	@Override
	public FavoriteStock update(FavoriteStock t) {
		return repository.save(t);
	}

	@Override
	public FavoriteStock retrieve(Long id) {
		return repository.findById(id);
	}

	@Override
	public Page<FavoriteStock> page(int page, int limit) {
		return repository.findAll(new PageRequest(page, limit));
	}

	@Override
	public List<FavoriteStock> list() {
		return repository.findAll();
	}

	@Override
	public FavoriteStock findByPublisherSerialCodeAndStockId(String serialCode, Long stockId) {
		return repository.findByPublisherSerialCodeAndStockId(serialCode, stockId);
	}

	@Override
	public Integer maxSort() {
		return repository.maxSort();
	}

	@Override
	public List<FavoriteStock> favoriteStockList(String serialCode) {
		Sort sort = new Sort(new Sort.Order(Direction.ASC, "sort"), new Sort.Order(Direction.DESC, "favoriteTime"));
		return repository.findByPublisherSerialCode(serialCode, sort);
	}

}
