package com.waben.stock.datalayer.publisher.service;

import java.util.Date;

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

	public Page<PaymentOrder> pages(final PaymentOrderQuery query){
		Pageable pageable = new PageRequest(query.getPage(), query.getSize());
		Page<PaymentOrder> pages = paymentOrderDao.page(new Specification<PaymentOrder>() {
			@Override
			public Predicate toPredicate(Root<PaymentOrder> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
				if(query.getPublisherId() != null){
					Predicate publisherIdQuery = cb.equal(root.get("publisherId").as(Long.class), query.getPublisherId());
					criteriaQuery.where(publisherIdQuery);
				}
				return criteriaQuery.getRestriction();
			}
		}, pageable);
		return pages;
	}
	
}
