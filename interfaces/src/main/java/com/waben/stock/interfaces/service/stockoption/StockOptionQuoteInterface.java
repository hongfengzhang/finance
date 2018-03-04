package com.waben.stock.interfaces.service.stockoption;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.waben.stock.interfaces.dto.stockoption.StockOptionQuoteDto;
import com.waben.stock.interfaces.pojo.Response;

public interface StockOptionQuoteInterface {

	@RequestMapping(value = "/{stockCode}/{cycle}/quote", method = RequestMethod.GET)
	Response<StockOptionQuoteDto> quote(@PathVariable("stockCode") String stockCode,
			@PathVariable("cycle") Integer cycle);

}
