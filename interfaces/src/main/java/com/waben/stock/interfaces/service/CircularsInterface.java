package com.waben.stock.interfaces.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.waben.stock.interfaces.dto.manage.CircularsDto;

public interface CircularsInterface {
	
	@RequestMapping(value = "/getEnabledCircularsList", method = RequestMethod.GET)
	public List<CircularsDto> getEnabledCircularsList();
	
}
