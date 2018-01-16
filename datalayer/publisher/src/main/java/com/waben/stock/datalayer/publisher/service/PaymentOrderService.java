package com.waben.stock.datalayer.publisher.service;

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

import com.waben.stock.datalayer.publisher.entity.PaymentOrder;
import com.waben.stock.datalayer.publisher.repository.PaymentOrderDao;
import com.waben.stock.interfaces.enums.PaymentState;
import com.waben.stock.interfaces.pojo.query.PaymentOrderQuery;

/**
 * 支付订单 Service
 * 
 * @author luomengan
 *
 */
@Service
public class PaymentOrderService {

	@Autowired
	private PaymentOrderDao paymentOrderDao;

	public PaymentOrder save(PaymentOrder paymentOrder) {
		paymentOrder.setCreateTime(new Date());
		return paymentOrderDao.create(paymentOrder);
	}

	public PaymentOrder changeState(String paymentNo, PaymentState state) {
		PaymentOrder paymentOrder = paymentOrderDao.retrieveByPaymentNo(paymentNo);
		paymentOrder.setState(state);
		paymentOrder.setUpdateTime(new Date());
		return paymentOrderDao.update(paymentOrder);
	}

	public PaymentOrder findByPaymentNo(String paymentNo) {
		return paymentOrderDao.retrieveByPaymentNo(paymentNo);
	}

	public Page<PaymentOrder> pagesByQuery(final PaymentOrderQuery query) {
		Pageable pageable = new PageRequest(query.getPage(), query.getSize());
		Page<PaymentOrder> pages = paymentOrderDao.page(new Specification<PaymentOrder>() {
			@Override
			public Predicate toPredicate(Root<PaymentOrder> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<>();
				if (query.getPublisherId() != null && query.getPublisherId() > 0) {
					predicateList
							.add(criteriaBuilder.equal(root.get("publisherId").as(Long.class), query.getPublisherId()));
				}
				if (query.getStates() != null && query.getStates().length > 0) {
					predicateList.add(root.get("state").in(query.getStates()));
				}
				if(query.getTypes() != null && query.getTypes().length > 0) {
					predicateList.add(root.get("type").in(query.getTypes()));
				}
				if (predicateList.size() > 0) {
					criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
				}
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("updateTime").as(Long.class)),
						criteriaBuilder.desc(root.get("createTime").as(Long.class)));
				return criteriaQuery.getRestriction();
			}
		}, pageable);
		return pages;
	}

}
