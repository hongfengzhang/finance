package com.waben.stock.futuresgateway.yisheng.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;

import com.waben.stock.futuresgateway.yisheng.dao.FuturesQuoteDao;
import com.waben.stock.futuresgateway.yisheng.dao.impl.jpa.FuturesQuoteRepository;
import com.waben.stock.futuresgateway.yisheng.entity.FuturesQuote;

/**
 * 期货合约行情 Dao实现
 * 
 * @author luomengan
 *
 */
@Repository
public class FuturesQuoteDaoImpl implements FuturesQuoteDao {

	@Autowired
	private FuturesQuoteRepository futuresQuoteRepository;

	@Override
	public FuturesQuote createFuturesQuote(FuturesQuote futuresQuote) {
		return futuresQuoteRepository.save(futuresQuote);
	}

	@Override
	public void deleteFuturesQuoteById(Long id) {
		futuresQuoteRepository.delete(id);
	}

	@Override
	public FuturesQuote updateFuturesQuote(FuturesQuote futuresQuote) {
		return futuresQuoteRepository.save(futuresQuote);
	}

	@Override
	public FuturesQuote retrieveFuturesQuoteById(Long id) {
		return futuresQuoteRepository.findById(id);
	}

	@Override
	public Page<FuturesQuote> pageFuturesQuote(int page, int limit) {
		return futuresQuoteRepository.findAll(new PageRequest(page, limit));
	}

	@Override
	public List<FuturesQuote> listFuturesQuote() {
		return futuresQuoteRepository.findAll();
	}

	@Override
	public List<FuturesQuote> retrieveByCommodityNoAndContractNoAndDateTimeStampLike(String commodityNo,
			String contractNo, String dateTimeStamp) {
		Sort sort = new Sort(new Sort.Order(Direction.ASC, "dateTimeStamp"));
		return futuresQuoteRepository.findByCommodityNoAndContractNoAndDateTimeStampLike(commodityNo, contractNo,
				dateTimeStamp, sort);
	}

	@Override
	public Long countByTimeGreaterThanEqual(Date time) {
		return futuresQuoteRepository.countByTimeGreaterThanEqual(time);
	}

	@Override
	public FuturesQuote retriveNewest(String commodityNo, String contractNo) {
		Pageable pageable = new PageRequest(0, 1, new Sort(new Sort.Order(Direction.DESC, "dateTimeStamp")));
		Page<FuturesQuote> pages = futuresQuoteRepository.findByCommodityNoAndContractNo(commodityNo, contractNo,
				pageable);
		if (pages.getContent() != null && pages.getContent().size() > 0) {
			return pages.getContent().get(0);
		}
		return null;
	}

}
