package com.waben.stock.futuresgateway.yisheng.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waben.stock.futuresgateway.yisheng.dao.FuturesQuoteDao;
import com.waben.stock.futuresgateway.yisheng.entity.FuturesQuote;

/**
 * 期货合约行情 Service
 * 
 * @author luomengan
 *
 */
@Service
public class FuturesQuoteService {

	@Autowired
	private FuturesQuoteDao quoteDao;

	public FuturesQuote getFuturesQuoteInfo(Long id) {
		return quoteDao.retrieveFuturesQuoteById(id);
	}

	@Transactional
	public FuturesQuote addFuturesQuote(FuturesQuote futuresQuote) {
		return quoteDao.createFuturesQuote(futuresQuote);
	}

	@Transactional
	public FuturesQuote modifyFuturesQuote(FuturesQuote futuresQuote) {
		return quoteDao.updateFuturesQuote(futuresQuote);
	}

	@Transactional
	public void deleteFuturesQuote(Long id) {
		quoteDao.deleteFuturesQuoteById(id);
	}

	@Transactional
	public void deleteFuturesQuotes(String ids) {
		if (ids != null) {
			String[] idArr = ids.split(",");
			for (String id : idArr) {
				if (!"".equals(id.trim())) {
					quoteDao.deleteFuturesQuoteById(Long.parseLong(id.trim()));
				}
			}
		}
	}

	public Page<FuturesQuote> futuresQuotes(int page, int limit) {
		return quoteDao.pageFuturesQuote(page, limit);
	}

	public List<FuturesQuote> list() {
		return quoteDao.listFuturesQuote();
	}

	public List<FuturesQuote> getByCommodityNoAndContractNoAndDateTimeStampLike(String commodityNo, String contractNo,
			String dateTimeStamp) {
		return quoteDao.retrieveByCommodityNoAndContractNoAndDateTimeStampLike(commodityNo, contractNo, dateTimeStamp);
	}

	public Long countByTimeGreaterThanEqual(Date time) {
		return quoteDao.countByTimeGreaterThanEqual(time);
	}

}
