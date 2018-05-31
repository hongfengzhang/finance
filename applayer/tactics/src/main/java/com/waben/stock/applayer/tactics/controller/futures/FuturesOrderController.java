package com.waben.stock.applayer.tactics.controller.futures;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.applayer.tactics.business.futures.FuturesOrderBusiness;
import com.waben.stock.applayer.tactics.security.SecurityUtil;
import com.waben.stock.interfaces.dto.futures.FuturesOrderDto;
import com.waben.stock.interfaces.pojo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 期货订单
 * 
 * @author sl
 *
 */
@RestController
@RequestMapping("/futuresOrder")
@Api(description = "期货订单")
public class FuturesOrderController {

	@Autowired
	private FuturesOrderBusiness futuresOrderBusiness;

	@GetMapping("/position")
	@ApiOperation(value = "获取持仓中列表")
	public Response<List<FuturesOrderDto>> getListFuturesOrderPositionByPublisherId() {
		return new Response<>(futuresOrderBusiness.getListFuturesOrderPositionByPublisherId(SecurityUtil.getUserId()));
	}

	@GetMapping("/entrust")
	@ApiOperation(value = "获取委托中列表")
	public Response<List<FuturesOrderDto>> getListFuturesOrderEntrustByPublisherId() {
		return new Response<>(futuresOrderBusiness.getListFuturesOrderEntrustByPublisherId(SecurityUtil.getUserId()));
	}

	@GetMapping("/settlement")
	@ApiOperation(value = "获取已结算列表")
	public Response<List<FuturesOrderDto>> getListFuturesOrderUnwindByPublisherId() {
		return new Response<>(futuresOrderBusiness.getListFuturesOrderUnwindByPublisherId(SecurityUtil.getUserId()));
	}

	@GetMapping("/position/profit")
	@ApiOperation(value = "获取持仓中总收益")
	public Response<BigDecimal> settlementOrderPositionByPublisherId() {
		return new Response<>(futuresOrderBusiness.settlementOrderPositionByPublisherId(SecurityUtil.getUserId()));
	}

	@GetMapping("/entrust/profit")
	@ApiOperation(value = "获取委托中总收益")
	public Response<BigDecimal> settlementOrderEntrustByPublisherId() {
		return new Response<>(futuresOrderBusiness.settlementOrderEntrustByPublisherId(SecurityUtil.getUserId()));
	}

	@GetMapping("/settlement/profit")
	@ApiOperation(value = "获取已结算总收益")
	public Response<BigDecimal> settlementOrderUnwindByPublisherId() {
		return new Response<>(futuresOrderBusiness.settlementOrderUnwindByPublisherId(SecurityUtil.getUserId()));
	}

}
