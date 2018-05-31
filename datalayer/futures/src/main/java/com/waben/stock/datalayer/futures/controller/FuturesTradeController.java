package com.waben.stock.datalayer.futures.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.datalayer.futures.service.FuturesOrderService;
import com.waben.stock.interfaces.dto.admin.futures.FuturesOrderAdminDto;
import com.waben.stock.interfaces.dto.admin.stockoption.StockOptionAdminDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.admin.futures.FuturesTradeAdminQuery;
import com.waben.stock.interfaces.service.futures.FuturesTradeInterface;
import com.waben.stock.interfaces.util.PageToPageInfo;

@RestController
@RequestMapping("/futuresTrade")
public class FuturesTradeController implements FuturesTradeInterface {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private FuturesOrderService futuresOrderService;
	
	@Override
	public Response<PageInfo<FuturesOrderAdminDto>> adminPagesByQuery(@RequestBody FuturesTradeAdminQuery query) {
		Page<FuturesOrderAdminDto> page = futuresOrderService.adminPagesByQuery(query);
		PageInfo<FuturesOrderAdminDto> result = PageToPageInfo.pageToPageInfo(page, FuturesOrderAdminDto.class);
		return new Response<>(result);
	}

}
