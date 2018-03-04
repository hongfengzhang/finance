package com.waben.stock.interfaces.service.stockoption;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.waben.stock.interfaces.dto.stockoption.StockOptionCycleDto;
import com.waben.stock.interfaces.pojo.Response;

public interface StockOptionCycleInterface {

	@RequestMapping(value = "/lists", method = RequestMethod.GET)
	Response<List<StockOptionCycleDto>> lists();

}
