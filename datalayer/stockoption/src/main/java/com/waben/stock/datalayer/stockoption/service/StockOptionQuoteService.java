package com.waben.stock.datalayer.stockoption.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waben.stock.datalayer.stockoption.entity.StockOptionOrg;
import com.waben.stock.datalayer.stockoption.entity.StockOptionOrgQuote;
import com.waben.stock.datalayer.stockoption.entity.StockOptionQuote;
import com.waben.stock.datalayer.stockoption.repository.StockOptionOrgDao;
import com.waben.stock.datalayer.stockoption.repository.StockOptionOrgQuoteDao;
import com.waben.stock.interfaces.util.CopyBeanUtils;

/**
 * 期权报价 Service
 * 
 * @author luomengan
 *
 */
@Service
public class StockOptionQuoteService {

	@Autowired
	private StockOptionOrgDao orgDao;

	@Autowired
	private StockOptionOrgQuoteDao orgQuoteDao;

	public StockOptionQuote quote(String stockCode, Integer cycle) {
		// TODO 此处目前只有一个机构，默认取第一个机构
		StockOptionOrg org = orgDao.retrieve(1L);
		StockOptionOrgQuote orgQuote = orgQuoteDao.findByOrgAndStockCodeAndCycle(org, stockCode, cycle);
		if (orgQuote != null) {
			StockOptionQuote result = CopyBeanUtils.copyBeanProperties(StockOptionQuote.class, orgQuote, false);
			BigDecimal rightMoneyRatio = result.getRightMoneyRatio();
			result.setRightMoneyRatio(rightMoneyRatio.add(rightMoneyRatio.multiply(new BigDecimal("0.1"))).setScale(4, RoundingMode.HALF_UP));
			return result;
		}
		return null;
	}

}
