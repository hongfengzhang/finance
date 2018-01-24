package com.waben.stock.applayer.tactics.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.applayer.tactics.reference.AppVersionReference;
import com.waben.stock.interfaces.dto.manage.AppVersionDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;

/**
 * app版本 Business
 * 
 * @author luomengan
 *
 */
@Service
public class AppVersionBusiness {

	@Autowired
	@Qualifier("appVersionReference")
	private AppVersionReference reference;

	public AppVersionDto getCurrentAppVersion() {
		Response<AppVersionDto> response = reference.getCurrentAppVersion();
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

}
