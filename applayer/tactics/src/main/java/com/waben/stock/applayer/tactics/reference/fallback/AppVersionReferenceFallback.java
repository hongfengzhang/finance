package com.waben.stock.applayer.tactics.reference.fallback;

import org.springframework.stereotype.Component;

import com.waben.stock.applayer.tactics.reference.AppVersionReference;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.manage.AppVersionDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;

/**
 * app版本 reference服务接口fallback
 *
 * @author luomengan
 */
@Component
public class AppVersionReferenceFallback implements AppVersionReference {

	@Override
	public Response<AppVersionDto> getCurrentAppVersion() {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

}
