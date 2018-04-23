package com.waben.stock.applayer.admin.reference.fallback;

import org.springframework.stereotype.Component;

import com.waben.stock.applayer.admin.reference.AppVersionUpgradeReference;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.manage.AppVersionUpgradeDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;

/**
 * app版本 reference服务接口fallback
 *
 * @author luomengan
 */
@Component
public class AppVersionUpgradeReferenceFallback implements AppVersionUpgradeReference {

	@Override
	public Response<AppVersionUpgradeDto> checkUpgrade(Integer versionCode, Integer deviceType, Integer shellIndex) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

}
