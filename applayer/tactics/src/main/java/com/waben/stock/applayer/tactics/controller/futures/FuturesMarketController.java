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
import io.swagger.annotations.ApiOperation;

/**
 * 期货合约行情
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/market")
@Api(description = "期货合约行情接口列表")
public class FuturesMarketController {

	@GetMapping("/{symbol}")
	@ApiOperation(value = "期货合约行情")
	public Response<FuturesContractMarket> market(@PathVariable("symbol") String symbol) {
		return new Response<>(RetriveFuturesOverHttp.market(symbol));
	}

	@GetMapping("/{symbol}/timeline")
	@ApiOperation(value = "期货合约分时图", notes = "dayCount表示统计的天数，不设置值默认为1天")
	public Response<List<FuturesContractLineData>> timeLine(@PathVariable("symbol") String symbol, Integer dayCount) {
		return new Response<>(RetriveFuturesOverHttp.timeLine(symbol, dayCount));
	}

	@GetMapping("/{symbol}/dayline")
	@ApiOperation(value = "期货合约日K线", notes = "startTime和endTime格式为:yyyy-MM-DD HH:mm:ss")
	public Response<List<FuturesContractLineData>> dayLine(@PathVariable("symbol") String symbol, String startTime,
			String endTime) {
		return new Response<>(RetriveFuturesOverHttp.dayLine(symbol, startTime, endTime));
	}

	@GetMapping("/{symbol}/minsline")
	@ApiOperation(value = "期货合约分K线", notes = "mins表示分钟， dayCount表示统计的天数，不设置值默认为1天")
	public Response<List<FuturesContractLineData>> minsLine(@PathVariable("symbol") String symbol, Integer mins,
			Integer dayCount) {
		return new Response<>(RetriveFuturesOverHttp.minsLine(symbol, mins, dayCount));
	}

}
