package com.waben.stock.applayer.promotion.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.applayer.promotion.business.CustomerBusiness;
import com.waben.stock.interfaces.dto.organization.CustomerDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.organization.CustomerQuery;

import io.swagger.annotations.Api;

/**
 * 客户 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/customer")
@Api(description = "客户接口列表")
public class CustomerController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	public CustomerBusiness customerBusiness;

	@RequestMapping(value = "/adminPage", method = RequestMethod.POST)
	public Response<PageInfo<CustomerDto>> adminPage(@RequestBody CustomerQuery query) {
		return new Response<>(customerBusiness.adminPage(query));
	}

}
