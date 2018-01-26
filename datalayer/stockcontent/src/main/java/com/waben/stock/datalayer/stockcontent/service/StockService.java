package com.waben.stock.datalayer.stockcontent.service;

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
import org.springframework.util.StringUtils;

import com.waben.stock.datalayer.stockcontent.entity.Stock;
import com.waben.stock.datalayer.stockcontent.repository.StockDao;
import com.waben.stock.interfaces.pojo.query.StockQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/***
 * @author yuyidi 2017-11-22 10:08:52
 * @class com.waben.stock.datalayer.stockcontent.service.StockService
 * @description
 */
@Service
public class StockService {

	@Autowired
	private StockDao stockDao;

	@Transactional
	public Stock saveStock(Stock stock) {
		return stockDao.create(stock);
	}

	public Page<Stock> pages(final StockQuery stockQuery) {
		Pageable pageable = new PageRequest(stockQuery.getPage(), stockQuery.getSize());
		Page<Stock> result = stockDao.page(new Specification<Stock>() {
			@Override
			public Predicate toPredicate(Root<Stock> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicatesList = new ArrayList();
				if (!StringUtils.isEmpty(stockQuery.getStockName())) {
					Predicate nameQuery = criteriaBuilder.equal(root.get("name").as(String.class),
							stockQuery.getStockName());
					predicatesList.add(criteriaBuilder.and(nameQuery));
				} else if (!StringUtils.isEmpty(stockQuery.getStockCode())) {
					Predicate codeQuery = criteriaBuilder.equal(root.get("code").as(String.class),
							stockQuery.getStockCode());
					predicatesList.add(criteriaBuilder.and(codeQuery));
				} else if (!StringUtils.isEmpty(stockQuery.getStatus())&&stockQuery.getStatus()!=2) {
					Predicate statusQuery = criteriaBuilder.equal(root.get("status").as(Integer.class),
							stockQuery.getStatus());
					predicatesList.add(criteriaBuilder.and(statusQuery));
				}
				criteriaQuery.where(predicatesList.toArray(new Predicate[predicatesList.size()]));
				return criteriaQuery.getRestriction();
			}
		}, pageable);
		return result;
	}

	public Stock findById(Long id) {
		return stockDao.retrieve(id);
	}

	public Stock findByCode(String code) {
		return stockDao.retrieveByCode(code);
	}

    public Integer revision(Stock stock) {
		return stockDao.updateById(stock.getStatus(),stock.getName(),stock.getCode(),stock.getId());
	}

	public void delete(Long id) {
		stockDao.delete(id);
	}
}
