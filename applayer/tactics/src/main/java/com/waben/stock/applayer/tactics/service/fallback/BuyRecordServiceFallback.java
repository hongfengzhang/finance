package com.waben.stock.applayer.tactics.service.fallback;

import org.springframework.stereotype.Component;

import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.service.buyrecord.BuyRecordInterface;

/**
 * 点买记录 断路器回调
 * 
 * @author luomengan
 *
 */
@Component
public class BuyRecordServiceFallback implements BuyRecordInterface {

	@Override
	public Response<BuyRecordDto> addBuyRecord(BuyRecordDto bindCardDto) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

}
