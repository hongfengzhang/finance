package com.waben.stock.datalayer.publisher.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.waben.stock.datalayer.publisher.entity.CapitalFlow;
import com.waben.stock.datalayer.publisher.entity.CapitalFlowExtend;
import com.waben.stock.datalayer.publisher.repository.CapitalFlowExtendDao;
import com.waben.stock.interfaces.enums.CapitalFlowExtendType;
import com.waben.stock.interfaces.enums.CapitalFlowType;
import com.waben.stock.interfaces.pojo.query.CapitalFlowExtendQuery;

/**
 * 资金流水扩展 Service
 * 
 * @author luomengan
 *
 */
@Service
public class CapitalFlowExtendService {

	@Autowired
	private CapitalFlowExtendDao dao;

	public Page<CapitalFlowExtend> pagesByQuery(final CapitalFlowExtendQuery query) {
		Pageable pageable = new PageRequest(query.getPage(), query.getSize());
		Page<CapitalFlowExtend> pages = dao.page(new Specification<CapitalFlowExtend>() {
			@Override
			public Predicate toPredicate(Root<CapitalFlowExtend> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<>();
				Join<CapitalFlowExtend, CapitalFlow> join = root.join("flow", JoinType.LEFT);
				if (query.getExtendType() != null) {
					predicateList.add(criteriaBuilder.equal(root.get("extendType").as(CapitalFlowExtendType.class),
							query.getExtendType()));
				}
				if (query.getExtendId() != null) {
					predicateList.add(criteriaBuilder.equal(root.get("extendId").as(Long.class), query.getExtendId()));
				}
				if (query.getType() != null) {
					predicateList
							.add(criteriaBuilder.equal(join.get("type").as(CapitalFlowType.class), query.getType()));
				}
				if (query.getPublisherId() != null) {
					predicateList
							.add(criteriaBuilder.equal(join.get("publisherId").as(Long.class), query.getPublisherId()));
				}
				if (predicateList.size() > 0) {
					criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
				}
				return criteriaQuery.getRestriction();
			}
		}, pageable);
		return pages;
	}

}
