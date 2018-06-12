package com.waben.stock.futuresgateway.yisheng.dao;

import java.util.List;

import org.springframework.data.domain.Page;

import com.waben.stock.futuresgateway.yisheng.entity.FuturesContractQuote;

/**
 * 期货合约行情 Dao
 * 
 * @author luomengan
 *
 */
public interface FuturesContractQuoteDao {

	public FuturesContractQuote createFuturesContractQuote(FuturesContractQuote futuresContractQuote);

	public void deleteFuturesContractQuoteById(Long id);

	public FuturesContractQuote updateFuturesContractQuote(FuturesContractQuote futuresContractQuote);

	public FuturesContractQuote retrieveFuturesContractQuoteById(Long id);

	public Page<FuturesContractQuote> pageFuturesContractQuote(int page, int limit);
	
	public List<FuturesContractQuote> listFuturesContractQuote();

	public List<FuturesContractQuote> retrieveByCommodityNoAndContractNoAndDateTimeStampLike(String commodityNo,
			String contractNo, String dateTimeStamp);

}
