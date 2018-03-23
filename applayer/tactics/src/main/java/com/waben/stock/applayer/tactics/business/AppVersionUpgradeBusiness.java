package com.waben.stock.applayer.tactics.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.applayer.tactics.reference.AppVersionUpgradeReference;
import com.waben.stock.interfaces.dto.manage.AppVersionUpgradeDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;

/**
 * app版本升级 Business
 * 
 * @author luomengan
 *
 */
@Service
public class AppVersionUpgradeBusiness {

	@Autowired
	@Qualifier("appVersionUpgradeInterface")
	private AppVersionUpgradeReference reference;

	public AppVersionUpgradeDto checkUpgrade(Integer versionCode, Integer deviceType, Integer shellIndex) {
		Response<AppVersionUpgradeDto> response = reference.checkUpgrade(versionCode, deviceType, shellIndex);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

}
