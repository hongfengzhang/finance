package com.waben.stock.futuresgateway.yisheng.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.futuresgateway.yisheng.entity.FuturesContractQuote;
import com.waben.stock.futuresgateway.yisheng.pojo.Response;
import com.waben.stock.futuresgateway.yisheng.service.FuturesContractQuoteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 期货合约行情 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/futuresContractQuote")
@Api(description = "期货合约行情接口列表")
public class FuturesContractQuoteController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	public FuturesContractQuoteService futuresContractQuoteService;

	@GetMapping("/{id}")
	@ApiOperation(value = "根据id获取期货合约行情")
	public Response<FuturesContractQuote> fetchById(@PathVariable Long id) {
		return new Response<>(futuresContractQuoteService.getFuturesContractQuoteInfo(id));
	}

	@GetMapping("/page")
	@ApiOperation(value = "获取期货合约行情分页数据")
	public Response<Page<FuturesContractQuote>> futuresContractQuotes(int page, int limit) {
		return new Response<>(
				(Page<FuturesContractQuote>) futuresContractQuoteService.futuresContractQuotes(page, limit));
	}

	@GetMapping("/list")
	@ApiOperation(value = "获取期货合约行情列表")
	public Response<List<FuturesContractQuote>> list() {
		return new Response<>(futuresContractQuoteService.list());
	}

	/******************************** 后台管理 **********************************/

	@PostMapping("/")
	@ApiOperation(value = "添加期货合约行情", hidden = true)
	public Response<FuturesContractQuote> addition(FuturesContractQuote futuresContractQuote) {
		return new Response<>(futuresContractQuoteService.addFuturesContractQuote(futuresContractQuote));
	}

	@PutMapping("/")
	@ApiOperation(value = "修改期货合约行情", hidden = true)
	public Response<FuturesContractQuote> modification(FuturesContractQuote futuresContractQuote) {
		return new Response<>(futuresContractQuoteService.modifyFuturesContractQuote(futuresContractQuote));
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除期货合约行情", hidden = true)
	public Response<Long> delete(@PathVariable Long id) {
		futuresContractQuoteService.deleteFuturesContractQuote(id);
		return new Response<Long>(id);
	}

	@PostMapping("/deletes")
	@ApiOperation(value = "批量删除期货合约行情（多个id以逗号分割）", hidden = true)
	public Response<Boolean> deletes(String ids) {
		futuresContractQuoteService.deleteFuturesContractQuotes(ids);
		return new Response<Boolean>(true);
	}

	@GetMapping("/adminList")
	@ApiOperation(value = "获取期货合约行情列表(后台管理)", hidden = true)
	public Response<List<FuturesContractQuote>> adminList() {
		return new Response<>(futuresContractQuoteService.list());
	}

}
