package com.waben.stock.datalayer.futures.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waben.stock.datalayer.futures.entity.FuturesOrder;
import com.waben.stock.datalayer.futures.repository.FuturesOrderDao;
import com.waben.stock.interfaces.enums.FuturesOrderState;
import com.waben.stock.interfaces.pojo.query.futures.FuturesOrderQuery;

/**
 * 期货订单 service
 * 
 * @author sunl
 *
 */
@Service
public class FuturesOrderService {

	@Autowired
	private FuturesOrderDao futuresOrderDao;

	public Page<FuturesOrder> pagesOrder(final FuturesOrderQuery query) {
		Pageable pageable = new PageRequest(query.getPage(), query.getSize());
		Page<FuturesOrder> pages = futuresOrderDao.page(new Specification<FuturesOrder>() {

			@Override
			public Predicate toPredicate(Root<FuturesOrder> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				// Join<FuturesExchange, FuturesContract> parentJoin =
				// root.join("exchange", JoinType.LEFT);

				if (predicateList.size() > 0) {
					criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
				}

				return criteriaQuery.getRestriction();
			}
		}, pageable);
		return pages;
	}

	@Transactional
	public FuturesOrder save(FuturesOrder order) {

		return order;
	}

	public FuturesOrder editOrder(Long id, FuturesOrderState state) {

		return futuresOrderDao.editOrder(id, state);
	}
}
