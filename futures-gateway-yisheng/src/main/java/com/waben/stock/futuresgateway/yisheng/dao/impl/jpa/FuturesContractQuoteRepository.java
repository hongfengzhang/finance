package com.waben.stock.futuresgateway.yisheng.dao.impl.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.Repository;

import com.waben.stock.futuresgateway.yisheng.entity.FuturesContractQuote;

/**
 * 期货合约行情 Repository
 * 
 * @author luomengan
 *
 */
public interface FuturesContractQuoteRepository extends Repository<FuturesContractQuote, Long> {

	FuturesContractQuote save(FuturesContractQuote futuresContractQuote);

	void delete(Long id);

	Page<FuturesContractQuote> findAll(Pageable pageable);

	List<FuturesContractQuote> findAll();

	FuturesContractQuote findById(Long id);

	List<FuturesContractQuote> findByCommodityNoAndContractNoAndDateTimeStampLike(String commodityNo, String contractNo,
			String dateTimeStamp, Sort sort);

}
