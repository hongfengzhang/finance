package com.waben.stock.interfaces.service.manage;

import java.util.List;

import com.waben.stock.interfaces.pojo.query.CircularsQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.waben.stock.interfaces.dto.manage.CircularsDto;
import com.waben.stock.interfaces.pojo.Response;

public interface CircularsInterface {
	
	@RequestMapping(value = "/",method = RequestMethod.GET)
	Response<List<CircularsDto>> fetchCirculars(@RequestParam(value = "enable",required = false) Boolean enable);

	@RequestMapping(value = "/pages", method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	Response<PageInfo<CircularsDto>> pages(@RequestBody CircularsQuery query);
	
}
