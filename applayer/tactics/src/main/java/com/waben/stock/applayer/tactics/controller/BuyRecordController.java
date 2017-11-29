package com.waben.stock.applayer.tactics.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.applayer.tactics.business.BuyRecordBusiness;
import com.waben.stock.applayer.tactics.dto.buyrecord.BuyRecordWithMarketDto;
import com.waben.stock.applayer.tactics.security.SecurityUtil;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.enums.BuyRecordState;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.BuyRecordQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 点买记录 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/buyRecord")
@Api(description = "点买交易")
public class BuyRecordController {

	@Autowired
	private BuyRecordBusiness buyRecordBusiness;

	@PostMapping("/buy")
	@ApiOperation(value = "点买")
	public Response<BuyRecordWithMarketDto> buy(Long strategyTypeId, BigDecimal applyAmount, BigDecimal serviceFee,
			BigDecimal reserveFund, BigDecimal profitPoint, BigDecimal lossPoint, String stockCode, Boolean deferred) {
		// TODO 以下代码需优化，放置到Service中处理

		// TODO 检查参数是否合理

		// TODO 检查余额

		// TODO 验证支付密码

		// 初始化点买数据
		BuyRecordDto dto = new BuyRecordDto();
		dto.setStrategyTypeId(strategyTypeId);
		dto.setApplyAmount(applyAmount);
		dto.setServiceFee(serviceFee);
		dto.setReserveFund(reserveFund);
		dto.setProfitPoint(profitPoint);
		dto.setLossPoint(lossPoint);
		dto.setStockCode(stockCode);
		dto.setDeferred(deferred);
		// 设置对应的publisher
		dto.setPublisherId(SecurityUtil.getUserId());
		dto.setPublisherSerialCode(SecurityUtil.getSerialCode());
		BuyRecordDto buyRecordDto = buyRecordBusiness.buy(dto);
		return new Response<>(buyRecordBusiness.wrapMarketInfo(buyRecordDto));
	}

	@GetMapping("/pagesHoldPosition")
	@ApiOperation(value = "持仓中的点买记录列表")
	public Response<PageInfo<BuyRecordWithMarketDto>> pagesHoldPosition(int page, int size) {
		BuyRecordQuery query = new BuyRecordQuery(page, size, SecurityUtil.getUserId(), new BuyRecordState[] {
				BuyRecordState.POSTED, BuyRecordState.BUYLOCK, BuyRecordState.HOLDPOSITION, BuyRecordState.SELLLOCK });
		PageInfo<BuyRecordDto> pageInfo = buyRecordBusiness.pages(query);
		List<BuyRecordWithMarketDto> content = buyRecordBusiness.wrapMarketInfo(pageInfo.getContent());
		return new Response<>(new PageInfo<>(content, pageInfo.getTotalPages(), pageInfo.getLast(),
				pageInfo.getTotalElements(), pageInfo.getSize(), pageInfo.getNumber(), pageInfo.getFrist()));
	}

	@GetMapping("/pagesUnwind")
	@ApiOperation(value = "结算的点买记录列表")
	public Response<PageInfo<BuyRecordWithMarketDto>> pagesUnwind(int page, int size) {
		BuyRecordQuery query = new BuyRecordQuery(page, size, SecurityUtil.getUserId(),
				new BuyRecordState[] { BuyRecordState.UNWIND });
		PageInfo<BuyRecordDto> pageInfo = buyRecordBusiness.pages(query);
		List<BuyRecordWithMarketDto> content = buyRecordBusiness.wrapMarketInfo(pageInfo.getContent());
		return new Response<>(new PageInfo<>(content, pageInfo.getTotalPages(), pageInfo.getLast(),
				pageInfo.getTotalElements(), pageInfo.getSize(), pageInfo.getNumber(), pageInfo.getFrist()));
	}

	@RequestMapping(value = "/selllock/{id}", method = RequestMethod.POST)
	Response<BuyRecordDto> sellLock(@PathVariable("id") Long id) {
		return new Response<>(buyRecordBusiness.sellLock(SecurityUtil.getUserId(), id, true));
	}

	// 该接口仅仅用来做测试，该接口应位于投资人服务中
	@RequestMapping(value = "/{investorId}/sellout/{id}", method = RequestMethod.POST)
	Response<BuyRecordDto> sellOut(@PathVariable("investorId") Long investorId, @PathVariable("id") Long id,
			@RequestParam(name = "sellingPrice") BigDecimal sellingPrice) {
		return new Response<>(buyRecordBusiness.sellOut(investorId, id, sellingPrice));
	}

}
