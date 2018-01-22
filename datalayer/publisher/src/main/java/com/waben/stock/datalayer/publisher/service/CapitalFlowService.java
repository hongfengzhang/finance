package com.waben.stock.datalayer.publisher.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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

import com.waben.stock.datalayer.publisher.entity.CapitalFlow;
import com.waben.stock.datalayer.publisher.repository.CapitalFlowDao;
import com.waben.stock.interfaces.pojo.query.CapitalFlowQuery;

/**
 * 资金流水 Service
 * 
 * @author luomengan
 *
 */
@Service
public class CapitalFlowService {

	@Autowired
	private CapitalFlowDao capitalFlowDao;

	public Page<CapitalFlow> pagesByQuery(final CapitalFlowQuery query) {
		Pageable pageable = new PageRequest(query.getPage(), query.getSize());
		Page<CapitalFlow> pages = capitalFlowDao.page(new Specification<CapitalFlow>() {
			@Override
			public Predicate toPredicate(Root<CapitalFlow> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<>();
				if (query.getPublisherId() != null && query.getPublisherId() > 0) {
					predicateList
							.add(criteriaBuilder.equal(root.get("publisherId").as(Long.class), query.getPublisherId()));
				}
				if (query.getTypes() != null && query.getTypes().length > 0) {
					predicateList.add(root.get("type").in(query.getTypes()));
				}
				if (query.getStartTime() != null) {
					predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("occurrenceTime").as(Date.class),
							query.getStartTime()));
				}
				if (query.getEndTime() != null) {
					predicateList.add(criteriaBuilder.lessThan(root.get("occurrenceTime").as(Date.class),
							query.getEndTime()));
				}
				if (predicateList.size() > 0) {
					criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
				}
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("occurrenceTime").as(Long.class)));
				return criteriaQuery.getRestriction();
			}
		}, pageable);
		return pages;
	}

	public BigDecimal promotionTotalAmount(Long publisherId) {
		return capitalFlowDao.promotionTotalAmount(publisherId);
	}

	
	public CapitalFlow findById(Long capitalFlowId){
		return capitalFlowDao.retrieve(capitalFlowId);
	}
}
