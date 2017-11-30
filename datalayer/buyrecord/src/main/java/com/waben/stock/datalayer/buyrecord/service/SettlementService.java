package com.waben.stock.datalayer.buyrecord.service;

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

import com.waben.stock.datalayer.buyrecord.entity.BuyRecord;
import com.waben.stock.datalayer.buyrecord.entity.Settlement;
import com.waben.stock.datalayer.buyrecord.repository.SettlementDao;
import com.waben.stock.interfaces.enums.BuyRecordState;
import com.waben.stock.interfaces.pojo.query.SettlementQuery;

/**
 * 结算 Service
 * 
 * @author luomengan
 *
 */
@Service
public class SettlementService {

	@Autowired
	private SettlementDao settlementDao;

	public Page<Settlement> pagesByQuery(final SettlementQuery query) {
		Pageable pageable = new PageRequest(query.getPage(), query.getSize());
		Page<Settlement> pages = settlementDao.page(new Specification<Settlement>() {
			@Override
			public Predicate toPredicate(Root<Settlement> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				Join<Settlement, BuyRecord> join = root.join("buyRecord", JoinType.LEFT);

				if (query.getPublisherId() != null && query.getPublisherId() > 0) {
					criteriaQuery.where(criteriaBuilder.and(
							criteriaBuilder.equal(join.get("state").as(BuyRecordState.class), BuyRecordState.UNWIND),
							criteriaBuilder.equal(join.get("publisherId").as(Long.class), query.getPublisherId())));
				} else if (query.getBuyRecordId() != null && query.getBuyRecordId() > 0) {
					criteriaQuery.where(criteriaBuilder.and(
							criteriaBuilder.equal(join.get("state").as(BuyRecordState.class), BuyRecordState.UNWIND),
							criteriaBuilder.equal(join.get("id").as(Long.class), query.getBuyRecordId())));
				}
				return criteriaQuery.getRestriction();
			}
		}, pageable);
		return pages;
	}

}
