package com.waben.stock.datalayer.stockoption.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.waben.stock.datalayer.stockoption.business.PriceMarkupConfigBusiness;
import com.waben.stock.datalayer.stockoption.entity.StockOptionCycle;
import com.waben.stock.datalayer.stockoption.entity.StockOptionOrg;
import com.waben.stock.datalayer.stockoption.entity.StockOptionOrgQuote;
import com.waben.stock.datalayer.stockoption.entity.StockOptionQuote;
import com.waben.stock.datalayer.stockoption.repository.StockOptionCycleDao;
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

	@Autowired
	private StockOptionCycleDao cycleDao;

	@Autowired
	private PriceMarkupConfigBusiness priceMarkupBusiness;

	@Value("${price.markup:0.1}")
	private String priceMarkup;

	public StockOptionQuote quote(Long publisherId, String stockCode, Integer cycle) {
		// TODO 此处目前只有一个机构，默认取第一个机构
		StockOptionOrg org = orgDao.retrieve(1L);
		StockOptionOrgQuote orgQuote = orgQuoteDao.findByOrgAndStockCodeAndCycle(org, stockCode, cycle);
		if (orgQuote != null) {
			StockOptionCycle cycleObj = cycleDao.retrieveByCycle(cycle);
			StockOptionQuote result = CopyBeanUtils.copyBeanProperties(StockOptionQuote.class, orgQuote, false);
			if (cycleObj == null) {
				// 使用spring cloud config中配置的加价比例
				BigDecimal rightMoneyRatio = result.getRightMoneyRatio();
				result.setRightMoneyRatio(rightMoneyRatio.add(rightMoneyRatio.multiply(new BigDecimal(priceMarkup)))
						.setScale(4, RoundingMode.HALF_EVEN));
			} else {
				List<BigDecimal> ratioList = priceMarkupBusiness.priceMarkupRatioList(Integer.valueOf(2),
						cycleObj.getId(), publisherId);
				if (ratioList != null && ratioList.size() > 0) {
					// 使用机构设置的加价比例
					BigDecimal orgRightMoneyRatio = result.getRightMoneyRatio();
					BigDecimal rightMoneyRatio = orgRightMoneyRatio.abs();
					for (BigDecimal ratio : ratioList) {
						rightMoneyRatio = rightMoneyRatio
								.add(rightMoneyRatio.multiply(ratio.divide(new BigDecimal("100"))))
								.setScale(4, RoundingMode.HALF_EVEN);
					}
					result.setRightMoneyRatio(rightMoneyRatio);
				} else {
					// 使用spring cloud config中配置的加价比例
					BigDecimal rightMoneyRatio = result.getRightMoneyRatio();
					result.setRightMoneyRatio(rightMoneyRatio.add(rightMoneyRatio.multiply(new BigDecimal(priceMarkup)))
							.setScale(4, RoundingMode.HALF_EVEN));
				}
			}
			return result;
		}
		return null;
	}

}
