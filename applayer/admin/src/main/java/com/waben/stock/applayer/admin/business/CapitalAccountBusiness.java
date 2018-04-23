package com.waben.stock.applayer.admin.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.applayer.admin.reference.CapitalAccountReference;
import com.waben.stock.interfaces.dto.admin.publisher.CapitalAccountAdminDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.admin.publisher.CapitalAccountAdminQuery;

/**
 * 资金账户 Business
 * 
 * @author luomengan
 */
@Service
public class CapitalAccountBusiness {

	@Autowired
	@Qualifier("capitalAccountReference")
	private CapitalAccountReference reference;

	public PageInfo<CapitalAccountAdminDto> adminPagesByQuery(CapitalAccountAdminQuery query) {
		Response<PageInfo<CapitalAccountAdminDto>> response = reference.adminPagesByQuery(query);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

}
