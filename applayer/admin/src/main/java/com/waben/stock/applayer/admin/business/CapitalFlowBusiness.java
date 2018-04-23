package com.waben.stock.applayer.admin.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.applayer.admin.reference.CapitalFlowReference;
import com.waben.stock.interfaces.dto.admin.publisher.CapitalFlowAdminDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.admin.publisher.CapitalFlowAdminQuery;

/**
 * 资金流水 Business
 * 
 * @author luomengan
 */
@Service
public class CapitalFlowBusiness {

	@Autowired
	@Qualifier("capitalFlowReference")
	private CapitalFlowReference reference;

	public PageInfo<CapitalFlowAdminDto> adminPagesByQuery(CapitalFlowAdminQuery query) {
		Response<PageInfo<CapitalFlowAdminDto>> response = reference.adminPagesByQuery(query);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

}
