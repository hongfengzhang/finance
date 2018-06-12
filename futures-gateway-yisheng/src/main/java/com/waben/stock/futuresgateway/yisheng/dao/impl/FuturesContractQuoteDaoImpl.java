package com.waben.stock.futuresgateway.yisheng.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;

import com.waben.stock.futuresgateway.yisheng.dao.FuturesContractQuoteDao;
import com.waben.stock.futuresgateway.yisheng.dao.impl.jpa.FuturesContractQuoteRepository;
import com.waben.stock.futuresgateway.yisheng.entity.FuturesContractQuote;

/**
 * 期货合约行情 Dao实现
 * 
 * @author luomengan
 *
 */
@Repository
public class FuturesContractQuoteDaoImpl implements FuturesContractQuoteDao {

	@Autowired
	private FuturesContractQuoteRepository futuresContractQuoteRepository;

	@Override
	public FuturesContractQuote createFuturesContractQuote(FuturesContractQuote futuresContractQuote) {
		return futuresContractQuoteRepository.save(futuresContractQuote);
	}

	@Override
	public void deleteFuturesContractQuoteById(Long id) {
		futuresContractQuoteRepository.delete(id);
	}

	@Override
	public FuturesContractQuote updateFuturesContractQuote(FuturesContractQuote futuresContractQuote) {
		return futuresContractQuoteRepository.save(futuresContractQuote);
	}

	@Override
	public FuturesContractQuote retrieveFuturesContractQuoteById(Long id) {
		return futuresContractQuoteRepository.findById(id);
	}

	@Override
	public Page<FuturesContractQuote> pageFuturesContractQuote(int page, int limit) {
		return futuresContractQuoteRepository.findAll(new PageRequest(page, limit));
	}

	@Override
	public List<FuturesContractQuote> listFuturesContractQuote() {
		return futuresContractQuoteRepository.findAll();
	}

	@Override
	public List<FuturesContractQuote> retrieveByCommodityNoAndContractNoAndDateTimeStampLike(String commodityNo,
			String contractNo, String dateTimeStamp) {
		Sort sort = new Sort(new Sort.Order(Direction.ASC, "dateTimeStamp"));
		return futuresContractQuoteRepository.findByCommodityNoAndContractNoAndDateTimeStampLike(commodityNo,
				contractNo, dateTimeStamp, sort);
	}

}
