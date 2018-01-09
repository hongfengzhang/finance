package com.waben.stock.applayer.strategist.reference.fallback;

import org.springframework.stereotype.Component;

import com.waben.stock.applayer.strategist.reference.DeferredRecordReference;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.buyrecord.DeferredRecordDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;

/**
 * 递延记录 reference服务接口fallback
 *
 * @author luomengan
 */
@Component
public class DeferredRecordReferenceFallback implements DeferredRecordReference {

	@Override
	public Response<DeferredRecordDto> fetchByPublisherIdAndBuyRecordId(Long publisherId, Long buyRecordId) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

}
