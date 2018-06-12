package com.waben.stock.futuresgateway.yisheng.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waben.stock.futuresgateway.yisheng.dao.FuturesContractQuoteDao;
import com.waben.stock.futuresgateway.yisheng.entity.FuturesContractQuote;

/**
 * 期货合约行情 Service
 * 
 * @author luomengan
 *
 */
@Service
public class FuturesContractQuoteService {

	@Autowired
	private FuturesContractQuoteDao futuresContractQuoteDao;

	public FuturesContractQuote getFuturesContractQuoteInfo(Long id) {
		return futuresContractQuoteDao.retrieveFuturesContractQuoteById(id);
	}

	@Transactional
	public FuturesContractQuote addFuturesContractQuote(FuturesContractQuote futuresContractQuote) {
		return futuresContractQuoteDao.createFuturesContractQuote(futuresContractQuote);
	}

	@Transactional
	public FuturesContractQuote modifyFuturesContractQuote(FuturesContractQuote futuresContractQuote) {
		return futuresContractQuoteDao.updateFuturesContractQuote(futuresContractQuote);
	}

	@Transactional
	public void deleteFuturesContractQuote(Long id) {
		futuresContractQuoteDao.deleteFuturesContractQuoteById(id);
	}

	@Transactional
	public void deleteFuturesContractQuotes(String ids) {
		if (ids != null) {
			String[] idArr = ids.split(",");
			for (String id : idArr) {
				if (!"".equals(id.trim())) {
					futuresContractQuoteDao.deleteFuturesContractQuoteById(Long.parseLong(id.trim()));
				}
			}
		}
	}

	public Page<FuturesContractQuote> futuresContractQuotes(int page, int limit) {
		return futuresContractQuoteDao.pageFuturesContractQuote(page, limit);
	}

	public List<FuturesContractQuote> list() {
		return futuresContractQuoteDao.listFuturesContractQuote();
	}

	public List<FuturesContractQuote> getByCommodityNoAndContractNoAndDateTimeStampLike(String commodityNo,
			String contractNo, String dateTimeStamp) {
		return futuresContractQuoteDao.retrieveByCommodityNoAndContractNoAndDateTimeStampLike(commodityNo, contractNo,
				dateTimeStamp);
	}

}
