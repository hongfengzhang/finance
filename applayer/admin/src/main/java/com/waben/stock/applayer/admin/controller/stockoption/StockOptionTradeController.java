package com.waben.stock.applayer.admin.controller.stockoption;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.applayer.admin.business.stockoption.StockOptionTradeBusiness;
import com.waben.stock.interfaces.dto.admin.stockoption.StockOptionAdminDto;
import com.waben.stock.interfaces.dto.stockoption.StockOptionOrgDto;
import com.waben.stock.interfaces.dto.stockoption.StockOptionTradeDto;
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

	@GetMapping("/orglist")
	@ApiOperation(value = "期权机构列表")
	public Response<List<StockOptionOrgDto>> orgList() {
		return new Response<>(business.orgList());
	}

	@GetMapping("/pages")
	@ApiOperation(value = "查询期权交易")
	public Response<PageInfo<StockOptionAdminDto>> pages(StockOptionQueryDto query) {
		return new Response<>(business.adminPagesByQuery(query));
	}

	@PutMapping(value = "/fail/{id}")
	@ApiOperation(value = "撤单")
	Response<StockOptionTradeDto> fail(@PathVariable("id") Long id) {
		return new Response<>(business.fail(id));
	}

	@PutMapping(value = "/turnover/{id}/org/{orgid}")
	@ApiOperation(value = "申购成交")
	Response<StockOptionTradeDto> turnover(@PathVariable("id") Long id, @PathVariable("orgid") Long orgid,
			@RequestParam("orgRightMoneyRatio") BigDecimal orgRightMoneyRatio,
			@RequestParam("buyingPrice") BigDecimal buyingPrice) {
		return new Response<>(business.turnover(id, orgid, orgRightMoneyRatio, buyingPrice));
	}

	@PutMapping(value = "/mark/{id}")
	@ApiOperation(value = "标记")
	Response<StockOptionTradeDto> mark(@PathVariable("id") Long id, @RequestParam("isMark") Boolean isMark) {
		return new Response<>(business.mark(id, isMark));
	}

	@PutMapping(value = "/dosettlement/{id}")
	@ApiOperation(value = "结算")
	Response<StockOptionTradeDto> settlement(@PathVariable("id") Long id,
			@RequestParam("sellingPrice") BigDecimal sellingPrice) {
		return new Response<>(business.settlement(id, sellingPrice));
	}

}
