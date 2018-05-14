package com.waben.stock.applayer.admin.controller.buyrecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.applayer.admin.business.buyrecord.BuyRecordBusiness;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.admin.buyrecord.BuyRecordAdminQuery;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/buyrecord")
@Api(description = "股票点买")
public class BuyRecordController {

	@Autowired
	private BuyRecordBusiness business;

	@GetMapping("/pages")
	@ApiOperation(value = "查询股票点买交易")
	public Response<PageInfo<BuyRecordDto>> pages(BuyRecordAdminQuery query) {
		return new Response<>(business.adminPagesByQuery(query));
	}

}
