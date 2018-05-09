package com.waben.stock.applayer.promotion.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.applayer.promotion.business.CustomerBusiness;
import com.waben.stock.applayer.promotion.business.OrganizationPublisherBusiness;
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
	private CustomerBusiness customerBusiness;

	@Autowired
	private OrganizationPublisherBusiness orgPublisherBusiness;

	@RequestMapping(value = "/adminPage", method = RequestMethod.GET)
	public Response<PageInfo<CustomerDto>> adminPage(CustomerQuery query) {
		return new Response<>(customerBusiness.adminPage(query));
	}

	@RequestMapping(value = "/publisherOrg/{publisherId}/{orgCode}", method = RequestMethod.POST)
	public Response<String> setPublisherOrg(@PathVariable("publisherId") Long publisherId,
			@PathVariable("orgCode") String orgCode) {
		orgPublisherBusiness.addOrgPublisher(publisherId, orgCode);
		Response<String> result = new Response<>();
		result.setResult("success");
		return new Response<>();
	}

}
