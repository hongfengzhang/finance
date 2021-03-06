package com.waben.stock.interfaces.service.organization;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.waben.stock.interfaces.dto.organization.OrganizationAccountDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.organization.OrganizationAccountQuery;

public interface OrganizationAccountInterface {

	@RequestMapping(value = "/orgId/{orgId}", method = RequestMethod.GET)
	Response<OrganizationAccountDto> fetchByOrgId(@PathVariable("orgId") Long orgId);

	@RequestMapping(value = "/{orgId}/modifyPaymentPassword", method = RequestMethod.PUT)
	Response<Void> modifyPaymentPassword(@PathVariable("orgId") Long orgId,
			@RequestParam(name = "oldPaymentPassword") String oldPaymentPassword,
			@RequestParam(name = "paymentPassword") String paymentPassword);

	@RequestMapping(value = "/pages", method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	Response<PageInfo<OrganizationAccountDto>> pages(@RequestBody OrganizationAccountQuery query);

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	Response<List<OrganizationAccountDto>> list();
}
