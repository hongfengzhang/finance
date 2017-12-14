package com.waben.stock.datalayer.publisher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waben.stock.datalayer.publisher.entity.PaymentOrder;
import com.waben.stock.datalayer.publisher.repository.PaymentOrderDao;
import com.waben.stock.interfaces.enums.PaymentState;

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
		return paymentOrderDao.create(paymentOrder);
	}

	public PaymentOrder changeState(String paymentNo, PaymentState state) {
		PaymentOrder paymentOrder = paymentOrderDao.retrieveByPaymentNo(paymentNo);
		paymentOrder.setState(state);
		return paymentOrderDao.update(paymentOrder);
	}

	public PaymentOrder findByPaymentNo(String paymentNo) {
		return paymentOrderDao.retrieveByPaymentNo(paymentNo);
	}

}
