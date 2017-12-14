package com.waben.stock.applayer.strategist.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.applayer.strategist.business.CapitalAccountBusiness;
import com.waben.stock.applayer.strategist.dto.publisher.CapitalStatisticDto;
import com.waben.stock.applayer.strategist.security.SecurityUtil;
import com.waben.stock.interfaces.dto.publisher.CapitalAccountDto;
import com.waben.stock.interfaces.pojo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 资金账户 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/capitalAccount")
@Api(description = "资金账户")
public class CapitalAccountController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CapitalAccountBusiness capitalAccountBusiness;

	@GetMapping("/")
	@ApiOperation(value = "获取当前资金账户")
	public Response<CapitalAccountDto> fetchCapitalAccount() {
		CapitalAccountDto result = capitalAccountBusiness.findByPublisherId(SecurityUtil.getUserId());
		result.setPaymentPassword(null);
		return new Response<>(result);
	}

	@GetMapping("/capitalSta")
	@ApiOperation(value = "我的持仓")
	public Response<CapitalStatisticDto> capitalSta() {
		CapitalStatisticDto result = new CapitalStatisticDto();
		Long publisherId = SecurityUtil.getUserId();
		result.setHoldProfitOrLoss(capitalAccountBusiness.getHoldProfitOrLoss(publisherId));
		result.setTotalApplyAmount(capitalAccountBusiness.getTotalApplyAmount(publisherId));
		result.setTodayApplyAmount(capitalAccountBusiness.getTodayApplyAmount(publisherId));
		result.setFrozenAmount(capitalAccountBusiness.findByPublisherId(publisherId).getFrozenCapital());
		result.setDeferredAmount(capitalAccountBusiness.getDeferredAmount(publisherId));

		return new Response<>(result);
	}

}
