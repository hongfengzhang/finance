package com.waben.stock.futuresgateway.yisheng.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.waben.stock.futuresgateway.yisheng.entity.FuturesQuoteMinuteK;

/**
 * 行情-分钟K Dao
 * 
 * @author luomengan
 *
 */
public interface FuturesQuoteMinuteKDao {

	public FuturesQuoteMinuteK createFuturesQuoteMinuteK(FuturesQuoteMinuteK futuresQuoteMinuteK);

	public void deleteFuturesQuoteMinuteKById(Long id);

	public FuturesQuoteMinuteK updateFuturesQuoteMinuteK(FuturesQuoteMinuteK futuresQuoteMinuteK);

	public FuturesQuoteMinuteK retrieveFuturesQuoteMinuteKById(Long id);

	public Page<FuturesQuoteMinuteK> pageFuturesQuoteMinuteK(int page, int limit);

	public List<FuturesQuoteMinuteK> listFuturesQuoteMinuteK();

	public FuturesQuoteMinuteK retrieveByCommodityNoAndContractNoAndTime(String commodityNo, String contractNo,
			Date time);

	public FuturesQuoteMinuteK retrieveNewestByCommodityNoAndContractNo(String commodityNo, String contractNo);

}
