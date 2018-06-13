package com.waben.stock.futuresgateway.yisheng.dao;

import java.util.List;

import org.springframework.data.domain.Page;

import com.waben.stock.futuresgateway.yisheng.entity.FuturesQuoteDayK;

/**
 * 行情-日K Dao
 * 
 * @author luomengan
 *
 */
public interface FuturesQuoteDayKDao {

	public FuturesQuoteDayK createFuturesQuoteDayK(FuturesQuoteDayK futuresQuoteDayK);

	public void deleteFuturesQuoteDayKById(Long id);

	public FuturesQuoteDayK updateFuturesQuoteDayK(FuturesQuoteDayK futuresQuoteDayK);

	public FuturesQuoteDayK retrieveFuturesQuoteDayKById(Long id);

	public Page<FuturesQuoteDayK> pageFuturesQuoteDayK(int page, int limit);
	
	public List<FuturesQuoteDayK> listFuturesQuoteDayK();

}
