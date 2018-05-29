package com.waben.stock.datalayer.futures.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.waben.stock.datalayer.futures.entity.FuturesCurrencyRate;
import com.waben.stock.datalayer.futures.repository.FuturesCurrencyRateDao;

/**
 * 期货货币汇率 service
 * 
 * @author sunl
 *
 */
@Service
public class FuturesCurrencyRateService {
	
	@Autowired
	private FuturesCurrencyRateDao currencyRateDao;
	
	public FuturesCurrencyRate addCurrencyRate(FuturesCurrencyRate rate) {
		return currencyRateDao.create(rate);
	}

	public FuturesCurrencyRate modifyCurrencyRate(FuturesCurrencyRate rate) {
		return currencyRateDao.update(rate);
	}

	public void deleteCurrencyRate(Long id) {
		currencyRateDao.delete(id);

	}
	
	public Page<FuturesCurrencyRate> list(){
		return currencyRateDao.page(0, Integer.MAX_VALUE);
	}

}
