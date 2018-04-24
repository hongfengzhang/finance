package com.waben.stock.applayer.admin.controller.stockoption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.applayer.admin.business.stockoption.StockOptionTradeBusiness;
import com.waben.stock.interfaces.dto.admin.stockoption.StockOptionAdminDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.admin.stockoption.StockOptionQueryDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 期权管理 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/stockOption")
@Api(description = "期权管理")
public class StockOptionTradeController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private StockOptionTradeBusiness business;

	@GetMapping("/pages")
	@ApiOperation(value = "查询期权交易")
	public Response<PageInfo<StockOptionAdminDto>> pages(StockOptionQueryDto query) {
		return new Response<>(business.adminPagesByQuery(query));
	}

}
