package com.waben.stock.datalayer.stockcontent.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;

import com.waben.stock.datalayer.stockcontent.entity.Stock;
import com.waben.stock.datalayer.stockcontent.repository.StockDao;
import com.waben.stock.datalayer.stockcontent.repository.impl.jpa.StockRecommendRepository;
import com.waben.stock.datalayer.stockcontent.repository.impl.jpa.StockRepository;
import com.waben.stock.interfaces.dto.stockcontent.StockRecommendDto;

/**
 * 股票 Dao实现
 * 
 * @author luomengan
 *
 */
@Repository
public class StockDaoImpl implements StockDao {

	@Autowired
	private StockRepository repository;

	@Autowired
	private StockRecommendRepository stockRecommendRepository;

	@Override
	public Stock create(Stock t) {
		return repository.save(t);
	}

	@Override
	public void delete(Long id) {
		repository.delete(id);
	}

	@Override
	public Stock update(Stock t) {
		return repository.save(t);
	}

	@Override
	public Stock retrieve(Long id) {
		return repository.findById(id);
	}

	@Override
	public Page<Stock> page(int page, int limit) {
		return repository.findAll(new PageRequest(page, limit));
	}

	@Override
	public List<Stock> list() {
		return repository.findAll();
	}

	@Override
	public List<Stock> selectStock(String keyword, Integer limit) {
		return repository.findByNameLikeOrCodeLikeOrPinyinAbbrLike("%" + keyword + "%", "%" + keyword + "%",
				"%" + keyword + "%", limit);
	}

	@Override
	public List<StockRecommendDto> getStockRecommendList() {
		Sort sort = new Sort(new Sort.Order(Direction.ASC, "sort"));
		return stockRecommendRepository.findAll(sort);
	}

}
