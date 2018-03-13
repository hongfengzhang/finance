package com.waben.stock.interfaces.service.organization;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.waben.stock.interfaces.dto.organization.OrganizationAccountDto;
import com.waben.stock.interfaces.pojo.Response;

public interface OrganizationAccountInterface {

	@RequestMapping(value = "/orgId/{orgId}", method = RequestMethod.GET)
	Response<OrganizationAccountDto> fetchByOrgId(@PathVariable("orgId") Long orgId);

	@RequestMapping(value = "/{orgId}/modifyPaymentPassword", method = RequestMethod.PUT)
	Response<Void> modifyPaymentPassword(@PathVariable("orgId") Long orgId,
			@RequestParam(name = "oldPaymentPassword") String oldPaymentPassword,
			@RequestParam(name = "paymentPassword") String paymentPassword);

}
