package com.waben.stock.applayer.strategist.reference.fallback;

import org.springframework.stereotype.Component;

import com.waben.stock.applayer.strategist.reference.RealNameReference;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.publisher.RealNameDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;

/**
 * 实名认证 reference服务接口fallback
 * 
 * @author luomengan
 *
 */
@Component
public class RealNameReferenceFallback implements RealNameReference {

	@Override
	public Response<RealNameDto> add(RealNameDto realName) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<RealNameDto> fetch(String resourceTypeIndex, Long resourceId) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}
	
}
