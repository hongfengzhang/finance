package com.waben.stock.applayer.tactics.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.applayer.tactics.security.SecurityUtil;
import com.waben.stock.applayer.tactics.service.BuyRecordService;
import com.waben.stock.applayer.tactics.service.CapitalAccountService;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.dto.publisher.CapitalAccountDto;
import com.waben.stock.interfaces.enums.InPositionTimeType;
import com.waben.stock.interfaces.pojo.Response;

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
	public Response<BuyRecordDto> buy(Integer positionType, BigDecimal applyAmount, BigDecimal serviceFee,
			BigDecimal frozenCapital, BigDecimal stopProfitPoint, BigDecimal stopLossPoint, String stockCode) {
		// TODO 检查参数是否合理

		// TODO 检查余额

		// 初始化点买数据
		BuyRecordDto dto = new BuyRecordDto();
		dto.setPositionType(InPositionTimeType.getByIndex(String.valueOf(positionType)));
		dto.setApplyAmount(applyAmount);
		dto.setServiceFee(serviceFee);
		dto.setFrozenCapital(frozenCapital);
		dto.setStopProfitPoint(stopProfitPoint);
		dto.setStopLossPoint(stopLossPoint);
		dto.setStockCode(stockCode);
		// TODO 设置递延费的快照
		dto.setDeferredCharges(new BigDecimal(18));
		// TODO 计算止盈点位和止损点位
		dto.setStopProfitPosition(applyAmount.multiply(stopProfitPoint));
		dto.setStopProfitWarnPosition(applyAmount.multiply(stopProfitPoint).subtract(new BigDecimal(100)));
		dto.setStopLossPosition(applyAmount.multiply(stopLossPoint));
		dto.setStopLossWarnPosition(applyAmount.multiply(stopLossPoint).add(new BigDecimal(100)));
		// 设置对应的publisher
		dto.setPublisherId(SecurityUtil.getUserId());
		dto.setPublisherSerialCode(SecurityUtil.getSerialCode());
		Response<BuyRecordDto> result = buyRecordService.addBuyRecord(dto);
		// 扣去金额、冻结保证金，增加流水记录
		Response<CapitalAccountDto> accountOperationResp = accountService.serviceFeeAndCompensateMoney(
				SecurityUtil.getUserId(), SecurityUtil.getSerialCode(), result.getResult().getId(),
				result.getResult().getSerialCode(), dto.getServiceFee(), dto.getFrozenCapital());
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
