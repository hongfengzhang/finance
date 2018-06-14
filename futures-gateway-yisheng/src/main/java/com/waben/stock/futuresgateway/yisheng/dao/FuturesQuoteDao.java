package com.waben.stock.futuresgateway.yisheng.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.waben.stock.futuresgateway.yisheng.entity.FuturesQuote;

/**
 * 期货合约行情 Dao
 * 
 * @author luomengan
 *
 */
public interface FuturesQuoteDao {

	public FuturesQuote createFuturesQuote(FuturesQuote futuresQuote);

	public void deleteFuturesQuoteById(Long id);

	public FuturesQuote updateFuturesQuote(FuturesQuote futuresQuote);

	public FuturesQuote retrieveFuturesQuoteById(Long id);

	public Page<FuturesQuote> pageFuturesQuote(int page, int limit);

	public List<FuturesQuote> listFuturesQuote();

	public List<FuturesQuote> retrieveByCommodityNoAndContractNoAndDateTimeStampLike(String commodityNo,
			String contractNo, String dateTimeStamp);

	public Long countByTimeGreaterThanEqual(Date time);

	public FuturesQuote retriveNewest(String commodityNo, String contractNo);

}
