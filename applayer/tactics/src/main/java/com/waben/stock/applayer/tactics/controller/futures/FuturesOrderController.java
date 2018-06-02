package com.waben.stock.applayer.tactics.controller.futures;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.applayer.tactics.business.PublisherBusiness;
import com.waben.stock.applayer.tactics.business.futures.FuturesContractBusiness;
import com.waben.stock.applayer.tactics.business.futures.FuturesOrderBusiness;
import com.waben.stock.applayer.tactics.dto.futures.FuturesOrderBuysellDto;
import com.waben.stock.applayer.tactics.dto.futures.FuturesOrderMarketDto;
import com.waben.stock.applayer.tactics.security.SecurityUtil;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.futures.FuturesContractDto;
import com.waben.stock.interfaces.dto.futures.FuturesOrderDto;
import com.waben.stock.interfaces.dto.publisher.CapitalAccountDto;
import com.waben.stock.interfaces.dto.publisher.PublisherDto;
import com.waben.stock.interfaces.enums.FuturesOrderState;
import com.waben.stock.interfaces.enums.FuturesTradePriceType;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.futures.FuturesContractQuery;
import com.waben.stock.interfaces.pojo.query.futures.FuturesOrderQuery;
import com.waben.stock.interfaces.util.PasswordCrypt;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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

	@Autowired
	private FuturesContractBusiness futuresContractBusiness;

	@Autowired
	private PublisherBusiness publisherBusiness;

	@PostMapping("/buy")
	@ApiOperation(value = "买涨买跌下单")
	public Response<FuturesOrderDto> placeOrder(FuturesOrderBuysellDto buysellDto) {
		FuturesContractQuery query = new FuturesContractQuery();
		query.setPage(0);
		query.setSize(1);
		query.setContractId(buysellDto.getContractId());
		// 判断该合约是否可用是否在交易中、网关是否支持该合约、合约交易所是否可用、以及是否在交易时间内
		FuturesContractDto contractDto = futuresContractBusiness.getContractByOne(query);

		// TODO 检查期货网关是否支持该合约

		// 用户单笔最大可交易数量
		BigDecimal perNum = contractDto.getPerOrderLimit();
		// 用户最大可持仓量
		BigDecimal userMaxNum = contractDto.getUserTotalLimit();
		// 用户持仓总数量
		// TODO 查询方法有误
		Integer sumUser = futuresOrderBusiness.sumUserNum(buysellDto.getContractId(), SecurityUtil.getUserId());
		BigDecimal sumUserNum = sumUser == null ? new BigDecimal(0) : new BigDecimal(sumUser).abs();
		// 当前用户单笔持仓数量
		BigDecimal userNum = buysellDto.getTotalQuantity();
		// 用户已持仓量 + 当前买入持仓量
		BigDecimal sumTotal = sumUserNum.add(buysellDto.getTotalQuantity());

		if (userNum.compareTo(perNum) > 0) {
			// 单笔交易数量过大
			throw new ServiceException(ExceptionConstant.SINGLE_TRANSACTION_QUANTITY_EXCEPTION);
		}
		if (perNum.compareTo(userMaxNum) > 0) {
			// 交易数量大于用户持仓总量
			throw new ServiceException(ExceptionConstant.CONTRACT_HOLDING_CAPACITY_INSUFFICIENT_EXCEPTION);
		}
		if (sumUserNum.abs().compareTo(userMaxNum) > 0 || sumTotal.compareTo(userMaxNum) > 0) {
			// 该用户持仓量已达上限
			throw new ServiceException(ExceptionConstant.UPPER_LIMIT_HOLDING_CAPACITY_EXCEPTION);
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
		// 保证金金额
		BigDecimal reserveAmount = contractDto.getPerUnitReserveFund().multiply(buysellDto.getTotalQuantity());
		// 开仓手续费 + 平仓手续费
		BigDecimal openUnwin = contractDto.getOpenwindServiceFee().add(contractDto.getUnwindServiceFee());

		// 交易综合费 = (开仓手续费 + 平仓手续费)* 交易持仓数
		BigDecimal comprehensiveAmount = openUnwin.multiply(buysellDto.getTotalQuantity());

		// 总金额 = 保证金金额 + 交易综合费
		totalFee = reserveAmount.add(comprehensiveAmount);

		// 检查余额
		if (totalFee.compareTo(capitalAccount.getAvailableBalance()) > 0) {
			throw new ServiceException(ExceptionConstant.AVAILABLE_BALANCE_NOTENOUGH_EXCEPTION);
		}
		FuturesOrderDto orderDto = new FuturesOrderDto();
		orderDto.setPublisherId(SecurityUtil.getUserId());
		orderDto.setOrderType(buysellDto.getOrderType());
		orderDto.setContractId(buysellDto.getContractId());
		orderDto.setTotalQuantity(buysellDto.getTotalQuantity());
		// 保证金
		orderDto.setReserveFund(reserveAmount);
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
		orderDto.setOvernightPerUnitDeferredFee(contractDto.getOvernightPerUnitDeferredFee());
		// 买入价格类型
		orderDto.setBuyingPriceType(buysellDto.getBuyingPriceType());
		// 止损类型及金额点位
		if (buysellDto.getLimitLossType() != null && buysellDto.getLimitLossType() > 0) {
			orderDto.setLimitLossType(buysellDto.getLimitLossType());
			orderDto.setPerUnitLimitLossAmount(buysellDto.getPerUnitLimitLossAmount());
		}
		// 止盈类型及金额点位
		if (buysellDto.getLimitProfitType() != null && buysellDto.getLimitProfitType() > 0) {
			orderDto.setLimitProfitType(buysellDto.getLimitProfitType());
			orderDto.setPerUnitLimitProfitAmount(buysellDto.getPerUnitLimitProfitAmount());
		}

		// 委托买入价格
		if (buysellDto.getBuyingPriceType() == FuturesTradePriceType.LMT) {
			orderDto.setBuyingEntrustPrice(buysellDto.getBuyingEntrustPrice());
		}
		// 获取是否为测试单
		PublisherDto publisher = publisherBusiness.findById(SecurityUtil.getUserId());
		orderDto.setIsTest(publisher.getIsTest());
		return new Response<>(futuresOrderBusiness.buy(orderDto));
	}

	@PostMapping("/applyUnwind/{orderId}")
	@ApiOperation(value = "用户申请平仓")
	public Response<FuturesOrderDto> applyUnwind(@PathVariable Long orderId,
			@RequestParam(required = true) FuturesTradePriceType sellingPriceType, BigDecimal sellingEntrustPrice) {

		return null;
	}

	@GetMapping("/holding")
	@ApiOperation(value = "获取持仓中列表")
	public Response<PageInfo<FuturesOrderMarketDto>> getListFuturesOrderHolding(int page, int size) {
		FuturesOrderQuery orderQuery = new FuturesOrderQuery();
		FuturesOrderState[] states = { FuturesOrderState.Position };
		orderQuery.setStates(states);
		orderQuery.setPage(page);
		orderQuery.setSize(size);
		orderQuery.setPublisherId(SecurityUtil.getUserId());
		return new Response<>(futuresOrderBusiness.pageOrderMarket(orderQuery));
	}

	@GetMapping("/entrustment")
	@ApiOperation(value = "获取委托中列表")
	public Response<PageInfo<FuturesOrderMarketDto>> getListFuturesOrderEntrustment(int page, int size) {
		FuturesOrderQuery orderQuery = new FuturesOrderQuery();
		FuturesOrderState[] states = { FuturesOrderState.BuyingEntrust, FuturesOrderState.PartPosition,
				FuturesOrderState.SellingEntrust, FuturesOrderState.PartUnwind };
		orderQuery.setStates(states);
		orderQuery.setPage(page);
		orderQuery.setSize(size);
		orderQuery.setPublisherId(SecurityUtil.getUserId());
		return new Response<>(futuresOrderBusiness.pageOrderMarket(orderQuery));
	}

	@GetMapping("/settled")
	@ApiOperation(value = "获取已结算列表")
	public Response<PageInfo<FuturesOrderMarketDto>> getListFuturesOrderSettled(int page, int size) {
		FuturesOrderQuery orderQuery = new FuturesOrderQuery();
		FuturesOrderState[] states = { FuturesOrderState.BuyingCanceled, FuturesOrderState.BuyingFailure,
				FuturesOrderState.Unwind };
		orderQuery.setStates(states);
		orderQuery.setPage(page);
		orderQuery.setSize(size);
		orderQuery.setPublisherId(SecurityUtil.getUserId());
		return new Response<>(futuresOrderBusiness.pageOrderMarket(orderQuery));
	}

	@GetMapping("/holding/profit")
	@ApiOperation(value = "获取持仓中总收益")
	public Response<BigDecimal> settlementFuturesOrderHolding(int page, int size) {
		FuturesOrderQuery orderQuery = new FuturesOrderQuery();
		FuturesOrderState[] states = { FuturesOrderState.Position };
		orderQuery.setStates(states);
		orderQuery.setPage(page);
		orderQuery.setSize(size);
		orderQuery.setPublisherId(SecurityUtil.getUserId());
		List<FuturesOrderMarketDto> list = futuresOrderBusiness.pageOrderMarket(orderQuery).getContent();
		BigDecimal totalIncome = new BigDecimal(0);
		for (FuturesOrderMarketDto futuresOrderMarketDto : list) {
			totalIncome = totalIncome.add(futuresOrderMarketDto.getPublisherProfitOrLoss());
		}
		return new Response<>(totalIncome.setScale(2, RoundingMode.DOWN));
	}

	@GetMapping("/entrustment/profit")
	@ApiOperation(value = "获取委托中总收益")
	public Response<BigDecimal> settlementFuturesOrderEntrustment(int page, int size) {
		FuturesOrderQuery orderQuery = new FuturesOrderQuery();
		FuturesOrderState[] states = { FuturesOrderState.BuyingEntrust, FuturesOrderState.PartPosition,
				FuturesOrderState.SellingEntrust, FuturesOrderState.PartUnwind };
		orderQuery.setStates(states);
		orderQuery.setPage(page);
		orderQuery.setSize(size);
		orderQuery.setPublisherId(SecurityUtil.getUserId());
		List<FuturesOrderMarketDto> list = futuresOrderBusiness.pageOrderMarket(orderQuery).getContent();
		BigDecimal totalIncome = new BigDecimal(0);
		for (FuturesOrderMarketDto futuresOrderMarketDto : list) {
			totalIncome = totalIncome.add(futuresOrderMarketDto.getPublisherProfitOrLoss());
		}
		return new Response<>(totalIncome.setScale(2, RoundingMode.DOWN));
	}

	@GetMapping("/settled/profit")
	@ApiOperation(value = "获取已结算总收益")
	public Response<BigDecimal> settlementFuturesOrderSettled(int page, int size) {
		FuturesOrderQuery orderQuery = new FuturesOrderQuery();
		FuturesOrderState[] states = { FuturesOrderState.Unwind };
		orderQuery.setStates(states);
		orderQuery.setPage(page);
		orderQuery.setSize(size);
		orderQuery.setPublisherId(SecurityUtil.getUserId());
		List<FuturesOrderMarketDto> list = futuresOrderBusiness.pageOrderMarket(orderQuery).getContent();
		BigDecimal totalIncome = new BigDecimal(0);
		for (FuturesOrderMarketDto futuresOrderMarketDto : list) {
			totalIncome = totalIncome.add(futuresOrderMarketDto.getPublisherProfitOrLoss());
		}
		return new Response<>(totalIncome.setScale(2, RoundingMode.DOWN));
	}

	@GetMapping("/detail/{orderId}")
	@ApiOperation(value = "获取期货订单已结算详细信息")
	@ApiImplicitParam(paramType = "path", dataType = "Long", name = "orderId", value = "期货订单ID", required = true)
	public Response<PageInfo<FuturesOrderMarketDto>> getOrderSettldDetails(@PathVariable("orderId") Long orderId) {
		FuturesOrderQuery orderQuery = new FuturesOrderQuery();
		orderQuery.setPage(0);
		orderQuery.setSize(1);
		orderQuery.setOrderId(orderId);
		orderQuery.setPublisherId(SecurityUtil.getUserId());
		return new Response<>(futuresOrderBusiness.pageOrderMarket(orderQuery));
	}

}
