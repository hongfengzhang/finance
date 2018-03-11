package com.waben.stock.applayer.promotion.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.applayer.promotion.reference.CustomerReference;
import com.waben.stock.interfaces.dto.organization.CustomerDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.organization.CustomerQuery;

/**
 * 推广系统推广的客户 Business
 * 
 * @author luomengan
 *
 */
@Service
public class CustomerBusiness {

	@Autowired
	@Qualifier("customerReference")
	private CustomerReference reference;

	public PageInfo<CustomerDto> adminPage(CustomerQuery query) {
		Response<PageInfo<CustomerDto>> response = reference.adminPage(query);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

}
