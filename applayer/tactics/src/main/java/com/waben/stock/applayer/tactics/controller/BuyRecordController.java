package com.waben.stock.applayer.tactics.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.applayer.tactics.security.SecurityUtil;
import com.waben.stock.applayer.tactics.service.BuyRecordService;
import com.waben.stock.applayer.tactics.service.CapitalAccountService;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.dto.publisher.CapitalAccountDto;
import com.waben.stock.interfaces.dto.publisher.FavoriteStockDto;
import com.waben.stock.interfaces.enums.BuyRecordStatus;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.util.SerialCodeGenerator;

import io.swagger.annotations.ApiOperation;

/**
 * 点买记录 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/buyRecord")
public class BuyRecordController {

	@Autowired
	private BuyRecordService buyRecordService;

	@Autowired
	private CapitalAccountService accountService;

	@PostMapping("/buy")
	@ApiOperation(value = "点买")
	public Response<BuyRecordDto> buy(Long strategyTypeId, BigDecimal applyAmount, BigDecimal serviceFee,
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
		dto.setSerialCode(SerialCodeGenerator.generate());
		dto.setStatus(BuyRecordStatus.POSTED);
		dto.setCreateTime(new Date());
		// TODO 计算止盈点位和止损点位
		dto.setProfitPosition(applyAmount.multiply(profitPoint));
		dto.setProfitWarnPosition(applyAmount.multiply(profitPoint).subtract(new BigDecimal(100)));
		dto.setLossPosition(applyAmount.multiply(lossPoint));
		dto.setLossWarnPosition(applyAmount.multiply(lossPoint).add(new BigDecimal(100)));
		// 设置对应的publisher
		dto.setPublisherId(SecurityUtil.getUserId());
		dto.setPublisherSerialCode(SecurityUtil.getSerialCode());
		Response<BuyRecordDto> result = buyRecordService.addBuyRecord(dto);
		// 扣去金额、冻结保证金，增加流水记录
		Response<CapitalAccountDto> accountOperationResp = accountService.serviceFeeAndReserveFund(
				SecurityUtil.getUserId(), result.getResult().getId(), result.getResult().getSerialCode(), serviceFee,
				reserveFund);
		if (!"200".equals(accountOperationResp.getCode())) {
			// TODO 扣款失败，删除点买记录
			result.setResult(null);
			result.setCode(accountOperationResp.getCode());
			result.setMessage(accountOperationResp.getMessage());
		}
		// TODO 触发监控点买记录
		return result;
	}

}
