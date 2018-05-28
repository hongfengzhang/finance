package com.waben.stock.applayer.tactics.controller.futures;

import java.math.BigDecimal;
import java.sql.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.util.PasswordCrypt;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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

	@GetMapping("/buy/fall")
	@ApiOperation(value = "买涨买跌")
	@ApiImplicitParam(paramType = "query", dataType = "FuturesOrderBuysellDto", name = "buysellDto", value = "参数")
	public Response<FuturesOrderDto> pagesContractById(@RequestParam("buysellDto") FuturesOrderBuysellDto buysellDto)
			throws Throwable {
		FuturesContractQuery query = new FuturesContractQuery();
		query.setPage(0);
		query.setSize(1);
		query.setContractId(buysellDto.getContractId());
		FuturesContractDto contractDto = futuresContractBusiness.getContractByOne(query);
		// 用户交易数量
		BigDecimal userNum = buysellDto.getTotalQuantity();
		// 合约最大持仓量
		BigDecimal perNum = contractDto.getTotalLimit();
		if (perNum.compareTo(userNum) == -1) {
			// 该合约持仓量不足
			throw new ServiceException(ExceptionConstant.BUYRECORD_NONTRADINGPERIOD_EXCEPTION);
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
		// 合计支付
		BigDecimal totalFee = new BigDecimal(0);
		//保证金金额
		BigDecimal perUnitReserveAmount = contractDto.getPerUnitReserveFund().multiply(buysellDto.getTotalQuantity());
		//交易综合费
		BigDecimal comprehensiveAmount = contractDto.getOpenwindServiceFee().add(contractDto.getUnwindServiceFee());
//		totalFee = contractDto.getPerUnitReserveFund().add(augend);

		// 检查余额
//		if (buysellDto.getSumAmount().compareTo(capitalAccount.getAvailableBalance()) > 0) {
//			throw new ServiceException(ExceptionConstant.AVAILABLE_BALANCE_NOTENOUGH_EXCEPTION);
//		}
		FuturesOrderDto orderDto = new FuturesOrderDto();
		orderDto.setPublisherId(SecurityUtil.getUserId());
		orderDto.setPostTime(new Date(System.currentTimeMillis()));
		orderDto.setOrderType(buysellDto.getOrderType());
		orderDto.setContract(contractDto);
		orderDto.setTotalQuantity(buysellDto.getTotalQuantity());
//		orderDto.setReserveFund(buysellDto.getReserveFund());
//		orderDto.setServiceFee(buysellDto.getServiceFee());
		return new Response<>(CopyBeanUtils.copyBeanProperties(FuturesOrderDto.class, contractDto, false));
	}

}
