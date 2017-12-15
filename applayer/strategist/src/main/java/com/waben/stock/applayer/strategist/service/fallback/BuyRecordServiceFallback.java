package com.waben.stock.applayer.strategist.service.fallback;

import java.math.BigDecimal;
import java.util.List;

import com.waben.stock.interfaces.enums.BuyRecordState;
import org.springframework.stereotype.Component;

import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.BuyRecordQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StrategyHoldingQuery;
import com.waben.stock.interfaces.pojo.query.StrategyPostedQuery;
import com.waben.stock.interfaces.pojo.query.StrategyUnwindQuery;
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
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<BuyRecordDto> addBuyRecord(BuyRecordDto bindCardDto) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<PageInfo<BuyRecordDto>> pagesByQuery(BuyRecordQuery BuyRecordQuery) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<Void> dropBuyRecord(Long buyRecordId) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<BuyRecordDto> buyLock(Long investorId, Long id, String delegateNumber) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<BuyRecordDto> buyInto(Long investorId, Long id, BigDecimal buyingPrice) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<BuyRecordDto> sellLock(Long investorId, Long id, String delegateNumber,
			String windControlTypeIndex) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<BuyRecordDto> sellOut(Long investorId, Long id, BigDecimal sellingPrice) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<BuyRecordDto> sellApply(Long publisherId, Long id) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<List<BuyRecordDto>> buyRecordsWithStatus(Integer buyRecordState) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<PageInfo<BuyRecordDto>> pagesByPostedQuery(StrategyPostedQuery strategyPostedQuery) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<PageInfo<BuyRecordDto>> pagesByHoldingQuery(StrategyHoldingQuery strategyHoldingQuery) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<PageInfo<BuyRecordDto>> pagesByUnwindQuery(StrategyUnwindQuery trategyUnwindQuery) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

}
