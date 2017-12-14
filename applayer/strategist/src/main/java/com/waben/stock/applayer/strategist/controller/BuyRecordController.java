package com.waben.stock.applayer.strategist.controller;

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

import com.waben.stock.applayer.strategist.business.BuyRecordBusiness;
import com.waben.stock.applayer.strategist.business.CapitalAccountBusiness;
import com.waben.stock.applayer.strategist.dto.buyrecord.BuyRecordWithMarketDto;
import com.waben.stock.applayer.strategist.dto.buyrecord.TradeDynamicDto;
import com.waben.stock.applayer.strategist.security.SecurityUtil;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.dto.publisher.CapitalAccountDto;
import com.waben.stock.interfaces.enums.BuyRecordState;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.BuyRecordQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.SettlementQuery;

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

	@Autowired
	private CapitalAccountBusiness capitalAccountBusiness;

	@PostMapping("/buy")
	@ApiOperation(value = "点买")
	public Response<BuyRecordWithMarketDto> buy(@RequestParam(required = true) Long strategyTypeId,
			@RequestParam(required = true) BigDecimal applyAmount, @RequestParam(required = true) BigDecimal serviceFee,
			@RequestParam(required = true) BigDecimal reserveFund,
			@RequestParam(required = true) BigDecimal delegatePrice,
			@RequestParam(required = true) BigDecimal profitPoint, @RequestParam(required = true) BigDecimal lossPoint,
			@RequestParam(required = true) String stockCode, @RequestParam(required = true) Boolean deferred,
			@RequestParam(required = true) String paymentPassword) {
		// TODO 检查参数是否合理

		// 检查余额
		CapitalAccountDto capitalAccount = capitalAccountBusiness.findByPublisherId(SecurityUtil.getUserId());
		if (serviceFee.add(reserveFund).compareTo(capitalAccount.getAvailableBalance()) > 0) {
			throw new ServiceException(ExceptionConstant.AVAILABLE_BALANCE_NOTENOUGH_EXCEPTION);
		}
		// 验证支付密码
		String storePaymentPassword = capitalAccount.getPaymentPassword();
		if (storePaymentPassword == null || "".equals(storePaymentPassword)) {
			throw new ServiceException(ExceptionConstant.PAYMENTPASSWORD_NOTSET_EXCEPTION);
		}
		if (!storePaymentPassword.equals(paymentPassword)) {
			throw new ServiceException(ExceptionConstant.PAYMENTPASSWORD_WRONG_EXCEPTION);
		}
		// 初始化点买数据
		BuyRecordDto dto = new BuyRecordDto();
		dto.setStrategyTypeId(strategyTypeId);
		dto.setApplyAmount(applyAmount);
		dto.setServiceFee(serviceFee);
		dto.setReserveFund(reserveFund);
		dto.setProfitPoint(profitPoint);
		dto.setLossPoint(lossPoint.abs().multiply(new BigDecimal(-1)));
		dto.setStockCode(stockCode);
		dto.setDeferred(deferred);
		dto.setDelegatePrice(delegatePrice);
		// 设置对应的publisher
		dto.setPublisherId(SecurityUtil.getUserId());
		dto.setPublisherSerialCode(SecurityUtil.getSerialCode());
		BuyRecordDto buyRecordDto = buyRecordBusiness.buy(dto);
		return new Response<>(buyRecordBusiness.wrapMarketInfo(buyRecordDto));
	}

	@GetMapping("/pagesHoldPosition")
	@ApiOperation(value = "持仓中的点买记录列表")
	public Response<PageInfo<BuyRecordWithMarketDto>> pagesHoldPosition(int page, int size) {
		BuyRecordQuery query = new BuyRecordQuery(page, size, SecurityUtil.getUserId(),
				new BuyRecordState[] { BuyRecordState.POSTED, BuyRecordState.BUYLOCK, BuyRecordState.HOLDPOSITION,
						BuyRecordState.SELLAPPLY, BuyRecordState.SELLLOCK });
		PageInfo<BuyRecordDto> pageInfo = buyRecordBusiness.pages(query);
		List<BuyRecordWithMarketDto> content = buyRecordBusiness.wrapMarketInfo(pageInfo.getContent());
		return new Response<>(new PageInfo<>(content, pageInfo.getTotalPages(), pageInfo.getLast(),
				pageInfo.getTotalElements(), pageInfo.getSize(), pageInfo.getNumber(), pageInfo.getFrist()));
	}

	@GetMapping("/pagesUnwind")
	@ApiOperation(value = "结算的点买记录列表")
	public Response<PageInfo<BuyRecordWithMarketDto>> pagesUnwind(int page, int size) {
		SettlementQuery query = new SettlementQuery(page, size);
		query.setPublisherId(SecurityUtil.getUserId());
		return new Response<>(buyRecordBusiness.pagesSettlement(query));
	}

	@GetMapping("/tradeDynamic")
	@ApiOperation(value = "交易动态列表")
	public Response<PageInfo<TradeDynamicDto>> tradeDynamic(int page, int size) {
		return new Response<>(buyRecordBusiness.tradeDynamic(page, size));
	}

	@RequestMapping(value = "/sellapply/{id}", method = RequestMethod.POST)
	@ApiOperation(value = "用户申请卖出")
	Response<BuyRecordDto> sellapply(@PathVariable("id") Long id) {
		return new Response<>(buyRecordBusiness.sellApply(SecurityUtil.getUserId(), id));
	}

}
