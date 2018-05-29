package com.waben.stock.applayer.tactics.controller.futures;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.applayer.tactics.business.futures.FuturesContractBusiness;
import com.waben.stock.applayer.tactics.dto.futures.FuturesOrderBuysellDto;
import com.waben.stock.applayer.tactics.security.SecurityUtil;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.futures.FuturesContractDto;
import com.waben.stock.interfaces.dto.futures.FuturesOrderDto;
import com.waben.stock.interfaces.dto.publisher.CapitalAccountDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.futures.FuturesContractQuery;
import com.waben.stock.interfaces.util.PasswordCrypt;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 期货合约 Controller
 * 
 * @author sunl
 *
 */
@RestController
@RequestMapping("/futuresContract")
@Api(description = "期货合约")
public class FuturesContractController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private FuturesContractBusiness futuresContractBusiness;

	@GetMapping("/pagesContract")
	@ApiOperation(value = "获取期货合约列表")
	public Response<PageInfo<FuturesContractDto>> pagesContract(int page, int size) throws Throwable {
		FuturesContractQuery query = new FuturesContractQuery();
		query.setPage(page);
		query.setSize(size);
		query.setContractId(0L);
		return new Response<>(futuresContractBusiness.pagesContract(query));
	}

	@GetMapping("/buy")
	@ApiOperation(value = "买涨买跌")
	public Response<FuturesOrderDto> pagesContractById(FuturesOrderBuysellDto buysellDto) throws Throwable {
		FuturesContractQuery query = new FuturesContractQuery();
		query.setPage(0);
		query.setSize(1);
		query.setContractId(buysellDto.getContractId());
		FuturesContractDto contractDto = futuresContractBusiness.getContractByOne(query);
		// 用户单笔最大可交易数量
		BigDecimal perNum = contractDto.getPerOrderLimit();
		// 用户最大可持仓量
		BigDecimal userMaxNum = contractDto.getUserTotalLimit();
		// 用户持仓总数量
		Integer sumUser = futuresContractBusiness.sumUserNum(buysellDto.getContractId(), SecurityUtil.getUserId());
		BigDecimal sumUserNum = sumUser == null ? new BigDecimal(0) : new BigDecimal(sumUser);
		// 当前用户单笔持仓数量
		BigDecimal userNum = buysellDto.getTotalQuantity();
		// 用户已持仓量 + 当前买入持仓量
		BigDecimal sumTotal = sumUserNum.add(buysellDto.getTotalQuantity());

		if (userNum.compareTo(perNum) > 0) {
			// 单笔交易数量过大
			throw new ServiceException(ExceptionConstant.SINGLE_TRANSACTION_QUANTITY_EXCEPTION);
		}
		if (sumUserNum.abs().compareTo(userMaxNum) > 0 || sumTotal.compareTo(userMaxNum) > 0) {
			// 该用户持仓量已达上限
			throw new ServiceException(ExceptionConstant.UPPER_LIMIT_HOLDING_CAPACITY_EXCEPTION);
		}

		if (perNum.compareTo(userMaxNum) == -1) {
			// 该合约持仓量不足
			throw new ServiceException(ExceptionConstant.CONTRACT_HOLDING_CAPACITY_INSUFFICIENT_EXCEPTION);
		}
		// 验证支付密码
		CapitalAccountDto capitalAccount = futuresContractBusiness.findByPublisherId(SecurityUtil.getUserId());
		String storePaymentPassword = capitalAccount.getPaymentPassword();
		if (storePaymentPassword == null || "".equals(storePaymentPassword)) {
			throw new ServiceException(ExceptionConstant.PAYMENTPASSWORD_NOTSET_EXCEPTION);
		}
		if (!PasswordCrypt.match(buysellDto.getPaymentPassword(), storePaymentPassword)) {
			throw new ServiceException(ExceptionConstant.PAYMENTPASSWORD_WRONG_EXCEPTION);
		}
		// 总金额
		BigDecimal totalFee = new BigDecimal(0);
		// 递延费
		BigDecimal deferredFee = new BigDecimal(0);
		// 保证金金额
		BigDecimal perUnitReserveAmount = contractDto.getPerUnitReserveFund().multiply(buysellDto.getTotalQuantity());

		// 开仓手续费 + 平仓手续费
		BigDecimal openUnwin = contractDto.getOpenwindServiceFee().add(contractDto.getUnwindServiceFee());

		// 交易综合费 = (开仓手续费 + 平仓手续费)* 交易持仓数
		BigDecimal comprehensiveAmount = openUnwin.multiply(buysellDto.getTotalQuantity());
		// 是否递延
		if (buysellDto.getDeferred()) {
			deferredFee = contractDto.getOvernightPerUnitDeferredFee().multiply(buysellDto.getTotalQuantity());
			// 总金额 = 保证金金额 + 交易综合费 + 递延费
			totalFee = perUnitReserveAmount.add(comprehensiveAmount).add(deferredFee);
		} else {
			// 总金额 = 保证金金额 + 交易综合费
			totalFee = perUnitReserveAmount.add(comprehensiveAmount);
		}

		// 检查余额
		if (totalFee.compareTo(capitalAccount.getAvailableBalance()) > 0) {
			throw new ServiceException(ExceptionConstant.AVAILABLE_BALANCE_NOTENOUGH_EXCEPTION);
		}
		FuturesOrderDto orderDto = new FuturesOrderDto();
		orderDto.setPublisherId(SecurityUtil.getUserId());
		orderDto.setOrderType(buysellDto.getOrderType());
		orderDto.setContract(contractDto);
		orderDto.setTotalQuantity(buysellDto.getTotalQuantity());
		// 保证金
		orderDto.setReserveFund(perUnitReserveAmount);
		// 服务费
		orderDto.setServiceFee(comprehensiveAmount);
		orderDto.setContractSymbol(contractDto.getSymbol());
		orderDto.setContractName(contractDto.getName());
		orderDto.setContractCurrency(contractDto.getCurrency());
		orderDto.setOpenwindServiceFee(contractDto.getOpenwindServiceFee());
		orderDto.setUnwindServiceFee(contractDto.getUnwindServiceFee());
		orderDto.setPerUnitUnwindPoint(contractDto.getPerUnitUnwindPoint());
		orderDto.setUnwindPointType(contractDto.getUnwindPointType());
		orderDto.setOvernightPerUnitReserveFund(contractDto.getOvernightPerUnitReserveFund());
		// 是否递延
		orderDto.setDeferred(buysellDto.getDeferred());
		orderDto.setOvernightPerUnitDeferredFee(deferredFee);
		// 买入价格类型
		orderDto.setBuyingPriceType(buysellDto.getBuyingPriceType());
		// 对应的开仓网关ID
		orderDto.setOpenGatewayOrderId(contractDto.getGatewayId());
		// 止损类型及金额点位
		orderDto.setLimitLossType(buysellDto.getLimitLossType());
		orderDto.setPerUnitLimitLossPosition(buysellDto.getPerUnitLimitLossAmount());
		// 止盈类型及金额点位
		orderDto.setLimitProfitType(buysellDto.getLimitProfitType());
		orderDto.setPerUnitLimitProfitPositon(buysellDto.getPerUnitLimitProfitAmount());

		// 委托买入价格
		if ((buysellDto.getBuyingPriceType().getIndex()).equals("2")) {
			orderDto.setBuyingEntrustPrice(buysellDto.getBuyingEntrustPrice());
		}

		return new Response<>(futuresContractBusiness.buy(orderDto));
	}
}
