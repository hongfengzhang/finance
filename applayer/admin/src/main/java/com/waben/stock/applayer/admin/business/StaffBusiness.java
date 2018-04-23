package com.waben.stock.applayer.admin.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.applayer.admin.reference.StaffReference;
import com.waben.stock.interfaces.dto.manage.StaffDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;

/**
 * 后台管理用户 Business
 * 
 * @author luomengan
 */
@Service
public class StaffBusiness {

	@Autowired
	@Qualifier("staffReference")
	private StaffReference reference;

	public StaffDto fetchByUserName(String username) {
		Response<StaffDto> response = reference.fetchByUserName(username);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

}
