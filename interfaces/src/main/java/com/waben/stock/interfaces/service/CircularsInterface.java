package com.waben.stock.interfaces.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.waben.stock.interfaces.dto.manage.CircularsDto;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.web.bind.annotation.RequestParam;

public interface CircularsInterface {
	
	@RequestMapping(value = "/",method = RequestMethod.GET)
	Response<List<CircularsDto>> fetchCirculars(@RequestParam(value = "enable",required = false) Boolean enable);
}
