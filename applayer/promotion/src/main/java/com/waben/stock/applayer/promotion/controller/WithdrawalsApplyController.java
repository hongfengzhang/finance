package com.waben.stock.applayer.promotion.controller;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.applayer.promotion.business.OrganizationAccountBusiness;
import com.waben.stock.applayer.promotion.business.WithdrawalsApplyBusiness;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.organization.OrganizationAccountDto;
import com.waben.stock.interfaces.dto.organization.WithdrawalsApplyDto;
import com.waben.stock.interfaces.enums.WithdrawalsApplyState;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.WithdrawalsApplyQuery;
import com.waben.stock.interfaces.util.PasswordCrypt;

import io.swagger.annotations.Api;

/**
 * 提现申请 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/withdrawalsApply")
@Api(description = "提现申请接口列表")
public class WithdrawalsApplyController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	public WithdrawalsApplyBusiness business;

	@Autowired
	private OrganizationAccountBusiness accountBusiness;

	@PreAuthorize("hasRole('APPLICATION_FOR_CASH')")
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public Response<WithdrawalsApplyDto> addition(@RequestParam(required = true) Long orgId,
			@RequestParam(required = true) BigDecimal amount, @RequestParam(required = true) String paymentPassword) {
		// 验证支付密码
		OrganizationAccountDto account = accountBusiness.fetchByOrgId(orgId);
		String storePaymentPassword = account.getPaymentPassword();
		if (storePaymentPassword == null || "".equals(storePaymentPassword)) {
			throw new ServiceException(ExceptionConstant.PAYMENTPASSWORD_NOTSET_EXCEPTION);
		}
		if (!PasswordCrypt.match(paymentPassword, storePaymentPassword)) {
			throw new ServiceException(ExceptionConstant.PAYMENTPASSWORD_WRONG_EXCEPTION);
		}
		// 检查余额
		if (amount.compareTo(account.getAvailableBalance()) > 0) {
			throw new ServiceException(ExceptionConstant.AVAILABLE_BALANCE_NOTENOUGH_EXCEPTION);
		}
		WithdrawalsApplyDto apply = new WithdrawalsApplyDto();
		apply.setOrgId(orgId);
		apply.setAmount(amount);
		return new Response<>(business.addition(apply));
	}

	@RequestMapping(value = "/pages", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Response<PageInfo<WithdrawalsApplyDto>> pagesByQuery(@RequestBody WithdrawalsApplyQuery applyQuery) {
		return new Response<>(business.pagesByQuery(applyQuery));
	}

	@PreAuthorize("hasRole('REFUSE_CASH')")
	@RequestMapping(value = "/refuse/{applyId}", method = RequestMethod.POST)
	public Response<WithdrawalsApplyDto> refuse(@PathVariable("applyId") Long applyId) {
		return new Response<>(business.changeState(applyId, WithdrawalsApplyState.REFUSED.getIndex()));
	}

}
