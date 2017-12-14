package com.waben.stock.applayer.strategist.service.fallback;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.BuyRecordQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
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
	public Response<BuyRecordDto> fetchBuyRecord(Long buyrecord) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public Response<BuyRecordDto> addBuyRecord(BuyRecordDto bindCardDto) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public Response<PageInfo<BuyRecordDto>> pagesByQuery(BuyRecordQuery BuyRecordQuery) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public Response<String> dropBuyRecord(Long buyRecordId) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public Response<BuyRecordDto> buyLock(Long investorId, Long id, String delegateNumber) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public Response<BuyRecordDto> buyInto(Long investorId, Long id, BigDecimal buyingPrice) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public Response<BuyRecordDto> sellLock(Long investorId, Long id, String delegateNumber,
			String windControlTypeIndex) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public Response<BuyRecordDto> sellOut(Long investorId, Long id, BigDecimal sellingPrice) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public Response<BuyRecordDto> sellApply(Long publisherId, Long id) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

}
