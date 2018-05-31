package com.waben.stock.applayer.tactics.controller.futures;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.interfaces.commonapi.retrivefutures.RetriveFuturesOverHttp;
import com.waben.stock.interfaces.commonapi.retrivefutures.bean.FuturesContractLineData;
import com.waben.stock.interfaces.commonapi.retrivefutures.bean.FuturesContractMarket;
import com.waben.stock.interfaces.pojo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 期货合约行情
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/futuresMarket")
@Api(description = "期货合约行情接口列表")
public class FuturesMarketController {

	@GetMapping("/{symbol}")
	@ApiOperation(value = "期货合约行情")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "symbol", value = "期货合约标识", dataType = "string", paramType = "path", required = true) })
	public Response<FuturesContractMarket> market(@PathVariable("symbol") String symbol) {
		return new Response<>(RetriveFuturesOverHttp.market(symbol));
	}

	@GetMapping("/{symbol}/timeline")
	@ApiOperation(value = "期货合约分时图")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "symbol", value = "期货合约标识", dataType = "string", paramType = "path", required = true),
			@ApiImplicitParam(name = "dayCount", value = "统计的天数，不设置值默认为1天", dataType = "integer", paramType = "query", required = false) })
	public Response<List<FuturesContractLineData>> timeLine(@PathVariable("symbol") String symbol, Integer dayCount) {
		return new Response<>(RetriveFuturesOverHttp.timeLine(symbol, dayCount));
	}

	@GetMapping("/{symbol}/dayline")
	@ApiOperation(value = "期货合约日K线", notes = "startTime和endTime格式为:yyyy-MM-DD HH:mm:ss")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "symbol", value = "期货合约标识", dataType = "string", paramType = "path", required = true),
			@ApiImplicitParam(name = "startTime", value = "开始时间", dataType = "string", paramType = "query", required = false),
			@ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "string", paramType = "query", required = false) })
	public Response<List<FuturesContractLineData>> dayLine(@PathVariable("symbol") String symbol, String startTime,
			String endTime) {
		return new Response<>(RetriveFuturesOverHttp.dayLine(symbol, startTime, endTime));
	}

	@GetMapping("/{symbol}/minsline")
	@ApiOperation(value = "期货合约分K线")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "symbol", value = "期货合约标识", dataType = "string", paramType = "path", required = true),
			@ApiImplicitParam(name = "mins", value = "分钟", dataType = "integer", paramType = "query", required = true),
			@ApiImplicitParam(name = "dayCount", value = "统计的天数，不设置值默认为1天", dataType = "integer", paramType = "query", required = false) })
	public Response<List<FuturesContractLineData>> minsLine(@PathVariable("symbol") String symbol, Integer mins,
			Integer dayCount) {
		return new Response<>(RetriveFuturesOverHttp.minsLine(symbol, mins, dayCount));
	}

}
