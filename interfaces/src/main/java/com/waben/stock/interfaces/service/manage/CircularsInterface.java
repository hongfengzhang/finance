package com.waben.stock.interfaces.service.manage;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.waben.stock.interfaces.dto.manage.CircularsDto;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.web.bind.annotation.RequestParam;

public interface CircularsInterface {
<<<<<<< HEAD:interfaces/src/main/java/com/waben/stock/interfaces/service/CircularsInterface.java
	
	@RequestMapping(value = "/",method = RequestMethod.GET)
	Response<List<CircularsDto>> fetchCirculars(@RequestParam(value = "enable",required = false) Boolean enable);
=======

	@RequestMapping(value = "/getByEnable", method = RequestMethod.GET)
	Response<List<CircularsDto>> getByEnable(boolean enable);

>>>>>>> 5091503fca7ec8bf69793ce6aebaded483393d07:interfaces/src/main/java/com/waben/stock/interfaces/service/manage/CircularsInterface.java
}
