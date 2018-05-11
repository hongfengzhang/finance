package com.waben.stock.applayer.promotion.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.applayer.promotion.business.OrganizationAccountFlowBusiness;
import com.waben.stock.applayer.promotion.business.OrganizationBusiness;
import com.waben.stock.applayer.promotion.security.SecurityUtil;
import com.waben.stock.interfaces.dto.organization.OrganizationAccountFlowWithTradeInfoDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.organization.OrganizationAccountFlowQuery;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/orgflow")
@Api(description = "结算管理")
public class OrganizationAccountFlowController {

	org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	public OrganizationAccountFlowBusiness organizationAccountFlowBusiness;

	@Autowired
	public OrganizationBusiness organizationBusiness;

	@RequestMapping(value = "/pagesWithTradeInfo", method = RequestMethod.GET)
	public Response<PageInfo<OrganizationAccountFlowWithTradeInfoDto>> pagesWithTradeInfo(
			OrganizationAccountFlowQuery query) {
		query.setCurrentOrgId(SecurityUtil.getUserDetails().getOrgId());
		return new Response<>(organizationAccountFlowBusiness.pagesWithTradeInfo(query));
	}

}
