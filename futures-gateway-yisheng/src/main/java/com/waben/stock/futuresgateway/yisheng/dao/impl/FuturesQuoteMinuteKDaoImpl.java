package com.waben.stock.futuresgateway.yisheng.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;

import com.waben.stock.futuresgateway.yisheng.dao.FuturesQuoteMinuteKDao;
import com.waben.stock.futuresgateway.yisheng.dao.impl.jpa.FuturesQuoteMinuteKRepository;
import com.waben.stock.futuresgateway.yisheng.entity.FuturesQuoteMinuteK;

/**
 * 行情-分钟K Dao实现
 * 
 * @author luomengan
 *
 */
@Repository
public class FuturesQuoteMinuteKDaoImpl implements FuturesQuoteMinuteKDao {

	@Autowired
	private FuturesQuoteMinuteKRepository futuresQuoteMinuteKRepository;

	@Override
	public FuturesQuoteMinuteK createFuturesQuoteMinuteK(FuturesQuoteMinuteK futuresQuoteMinuteK) {
		return futuresQuoteMinuteKRepository.save(futuresQuoteMinuteK);
	}

	@Override
	public void deleteFuturesQuoteMinuteKById(Long id) {
		futuresQuoteMinuteKRepository.delete(id);
	}

	@Override
	public FuturesQuoteMinuteK updateFuturesQuoteMinuteK(FuturesQuoteMinuteK futuresQuoteMinuteK) {
		return futuresQuoteMinuteKRepository.save(futuresQuoteMinuteK);
	}

	@Override
	public FuturesQuoteMinuteK retrieveFuturesQuoteMinuteKById(Long id) {
		return futuresQuoteMinuteKRepository.findById(id);
	}

	@Override
	public Page<FuturesQuoteMinuteK> pageFuturesQuoteMinuteK(int page, int limit) {
		return futuresQuoteMinuteKRepository.findAll(new PageRequest(page, limit));
	}

	@Override
	public List<FuturesQuoteMinuteK> listFuturesQuoteMinuteK() {
		return futuresQuoteMinuteKRepository.findAll();
	}

	@Override
	public FuturesQuoteMinuteK retrieveByCommodityNoAndContractNoAndTime(String commodityNo, String contractNo,
			Date time) {
		return futuresQuoteMinuteKRepository.findByCommodityNoAndContractNoAndTime(commodityNo, contractNo, time);
	}

	@Override
	public FuturesQuoteMinuteK retrieveNewestByCommodityNoAndContractNo(String commodityNo, String contractNo) {
		Sort sort = new Sort(new Sort.Order(Direction.DESC, "time"));
		List<FuturesQuoteMinuteK> list = futuresQuoteMinuteKRepository.findByCommodityNoAndContractNo(commodityNo,
				contractNo, sort);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<FuturesQuoteMinuteK> retriveByCommodityNoAndContractNoAndTimeStrLike(String commodityNo,
			String contractNo, String timeStr) {
		Sort sort = new Sort(new Sort.Order(Direction.ASC, "time"));
		return futuresQuoteMinuteKRepository.findByCommodityNoAndContractNoAndTimeStrLike(commodityNo, contractNo,
				timeStr, sort);
	}

	@Override
	public List<FuturesQuoteMinuteK> retrieveByCommodityNoAndContractNoAndTimeGreaterThanEqualAndTimeLessThan(
			String commodityNo, String contractNo, Date startTime, Date endTime) {
		Sort sort = new Sort(new Sort.Order(Direction.ASC, "time"));
		return futuresQuoteMinuteKRepository.findByCommodityNoAndContractNoAndTimeGreaterThanEqualAndTimeLessThan(
				commodityNo, contractNo, startTime, endTime, sort);
	}

}
