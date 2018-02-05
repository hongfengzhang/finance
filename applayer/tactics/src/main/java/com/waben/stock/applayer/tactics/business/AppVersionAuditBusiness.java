package com.waben.stock.applayer.tactics.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.applayer.tactics.reference.AppVersionAuditReference;
import com.waben.stock.interfaces.dto.manage.AppVersionAuditDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;

/**
 * app版本 Business
 * 
 * @author luomengan
 *
 */
@Service
public class AppVersionAuditBusiness {

	@Autowired
	@Qualifier("appVersionReference")
	private AppVersionAuditReference reference;

	public AppVersionAuditDto getAppVersionAudit(Integer deviceType, Integer shellIndex) {
		Response<AppVersionAuditDto> response = reference.getAppVersionAudit(deviceType, shellIndex);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

}
