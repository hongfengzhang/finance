package com.waben.stock.datalayer.futures.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.waben.stock.datalayer.futures.entity.FuturesOrder;
import com.waben.stock.datalayer.futures.repository.FuturesOrderDao;
import com.waben.stock.datalayer.futures.repository.impl.jpa.FuturesOrderRepository;

@Repository
public class FuturesOrderDaoImpl implements FuturesOrderDao {

	@Autowired
	private FuturesOrderRepository futuresOrderRepository;
	
	@Override
	public FuturesOrder create(FuturesOrder futuresOrder) {
		return futuresOrderRepository.save(futuresOrder);
	}

	@Override
	public void delete(Long id) {
		futuresOrderRepository.delete(id);
	}

	@Override
	public FuturesOrder update(FuturesOrder futuresOrder) {
		return futuresOrderRepository.save(futuresOrder);
	}

	@Override
	public FuturesOrder retrieve(Long id) {
		return futuresOrderRepository.findById(id);
	}

	@Override
	public Page<FuturesOrder> page(int page, int limit) {
		return null;
	}

	@Override
	public Page<FuturesOrder> page(Specification<FuturesOrder> specification, Pageable pageable) {
		return futuresOrderRepository.findAll(specification, pageable);
	}

	@Override
	public List<FuturesOrder> list() {
		return futuresOrderRepository.findAll();
	}

	@Override
	public Integer countStockOptionTradeState(Long publisherId) {
		// TODO Auto-generated method stub
		return null;
	}

}
