package com.waben.stock.applayer.strategist.business;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.applayer.strategist.reference.StockOptionQuoteReference;
import com.waben.stock.interfaces.dto.stockoption.StockOptionQuoteDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;

/**
 * 期权报价 Business
 * 
 * @author luomengan
 *
 */
@Service
public class StockOptionQuoteBusiness {

	@Autowired
	@Qualifier("stockOptionQuoteReference")
	private StockOptionQuoteReference quoteReference;

	public StockOptionQuoteDto quote(Long publisherId, String stockCode, Integer cycle, BigDecimal nominalAmount) {
		Response<StockOptionQuoteDto> response = quoteReference.quote(publisherId, stockCode, cycle, nominalAmount);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

}
