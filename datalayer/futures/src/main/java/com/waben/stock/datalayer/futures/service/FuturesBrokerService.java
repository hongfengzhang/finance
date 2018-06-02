package com.waben.stock.datalayer.futures.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waben.stock.datalayer.futures.entity.FuturesBroker;
import com.waben.stock.datalayer.futures.repository.FuturesBrokerDao;

/**
 * 期货券商service
 * 
 * @author sunl
 *
 */
@Service
public class FuturesBrokerService {

	@Autowired
	private FuturesBrokerDao brokerDao;

	public FuturesBroker findByBrokerId(Long id) {
		return brokerDao.retrieve(id);
	}
}
