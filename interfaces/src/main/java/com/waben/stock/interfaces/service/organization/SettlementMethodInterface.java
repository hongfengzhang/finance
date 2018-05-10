package com.waben.stock.interfaces.service.organization;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.waben.stock.interfaces.dto.organization.SettlementMethodDto;
import com.waben.stock.interfaces.pojo.Response;

public interface SettlementMethodInterface {

	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	Response<SettlementMethodDto> edit(@RequestParam(required = false) Long id, @RequestParam(required = false) Integer type);
}
