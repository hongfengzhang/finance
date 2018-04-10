package com.waben.stock.interfaces.service.activity;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.waben.stock.interfaces.dto.activity.ActivityDto;
import com.waben.stock.interfaces.pojo.Response;

/**
 * 
 * 
 * @author guowei 2018/4/10
 *
 */
public interface ActivityMngInterface {
	
	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	Response<ActivityDto> addActivity(@RequestBody ActivityDto adto);
}
