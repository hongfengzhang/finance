package com.waben.stock.datalayer.publisher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waben.stock.datalayer.publisher.entity.WithdrawalsOrder;
import com.waben.stock.datalayer.publisher.repository.WithdrawalsOrderDao;
import com.waben.stock.interfaces.enums.WithdrawalsState;

/**
 * 提现订单 Service
 * 
 * @author luomengan
 *
 */
@Service
public class WithdrawalsOrderService {

	@Autowired
	private WithdrawalsOrderDao withdrawalsOrderDao;

	public WithdrawalsOrder save(WithdrawalsOrder withdrawalsOrder) {
		return withdrawalsOrderDao.create(withdrawalsOrder);
	}

	public WithdrawalsOrder changeState(String withdrawalsNo, WithdrawalsState state) {
		WithdrawalsOrder withdrawalsOrder = withdrawalsOrderDao.retrieveByWithdrawalsNo(withdrawalsNo);
		withdrawalsOrder.setState(state);
		return withdrawalsOrderDao.update(withdrawalsOrder);
	}

	public WithdrawalsOrder findByWithdrawalsNo(String withdrawalsNo) {
		return withdrawalsOrderDao.retrieveByWithdrawalsNo(withdrawalsNo);
	}

}
