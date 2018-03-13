package com.waben.stock.interfaces.service.organization;

import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.organization.OrganizationAccountQuery;
import com.waben.stock.interfaces.pojo.query.organization.OrganizationQuery;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.waben.stock.interfaces.dto.organization.OrganizationAccountDto;
import com.waben.stock.interfaces.pojo.Response;

public interface OrganizationAccountInterface {

	@RequestMapping(value = "/orgId/{orgId}", method = RequestMethod.GET)
	Response<OrganizationAccountDto> fetchByOrgId(@PathVariable("orgId") Long orgId);

	@RequestMapping(value = "/{orgId}/modifyPaymentPassword", method = RequestMethod.PUT)
	Response<Void> modifyPaymentPassword(@PathVariable("orgId") Long orgId,
			@RequestParam(name = "oldPaymentPassword") String oldPaymentPassword,
			@RequestParam(name = "paymentPassword") String paymentPassword);

	@RequestMapping(value = "/pages", method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	Response<PageInfo<OrganizationAccountDto>> pages(@RequestBody OrganizationAccountQuery query);
}
