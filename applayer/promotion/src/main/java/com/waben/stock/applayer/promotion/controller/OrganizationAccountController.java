package com.waben.stock.applayer.promotion.controller;

import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.organization.OrganizationAccountQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.applayer.promotion.business.OrganizationAccountBusiness;
import com.waben.stock.interfaces.dto.organization.OrganizationAccountDto;
import com.waben.stock.interfaces.pojo.Response;

import io.swagger.annotations.Api;

/**
 * 机构账户 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/organizationAccount")
@Api(description = "机构账户接口列表")
public class OrganizationAccountController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	public OrganizationAccountBusiness accountBusiness;

	@RequestMapping(value = "/{orgId}/modifyPaymentPassword", method = RequestMethod.PUT)
	public Response<Void> modifyPaymentPassword(@PathVariable("orgId") Long orgId, String oldPaymentPassword, String paymentPassword) {
		accountBusiness.modifyPaymentPassword(orgId, oldPaymentPassword, paymentPassword);
		return new Response<>();
	}

	@RequestMapping(value = "/orgId/{orgId}", method = RequestMethod.GET)
	public Response<OrganizationAccountDto> fetchByOrgId(@PathVariable("orgId") Long orgId) {
		return new Response<>(accountBusiness.fetchByOrgId(orgId));
	}

	@RequestMapping(value = "/pages",method = RequestMethod.GET)
	public Response<PageInfo<OrganizationAccountDto>> pages(OrganizationAccountQuery query){
		return new Response<>(accountBusiness.pages(query));
	}
}
