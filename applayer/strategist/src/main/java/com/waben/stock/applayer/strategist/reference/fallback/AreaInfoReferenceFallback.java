package com.waben.stock.applayer.strategist.reference.fallback;

import java.util.List;

import org.springframework.stereotype.Component;

import com.waben.stock.applayer.strategist.reference.AreaInfoReference;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.manage.AreaInfoDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;

/**
 * 区域 reference服务接口fallback
 *
 * @author luomengan
 */
@Component
public class AreaInfoReferenceFallback implements AreaInfoReference {

	@Override
	public Response<List<AreaInfoDto>> fetchByParentCode(String parentCode) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<AreaInfoDto> fetchByCode(String code) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

}
