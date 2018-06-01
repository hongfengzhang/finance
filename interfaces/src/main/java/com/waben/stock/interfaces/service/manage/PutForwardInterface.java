package com.waben.stock.interfaces.service.manage;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.waben.stock.interfaces.dto.admin.futures.PutForwardDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;

@FeignClient(name = "manage", path = "putforward", qualifier = "putForwardInterface")
public interface PutForwardInterface {

	@RequestMapping(value = "/pages", method = RequestMethod.POST, consumes = "application/json")
	Response<PageInfo<PutForwardDto>> pages();
	
	@RequestMapping(value = "/saveAndModify", method = RequestMethod.POST, consumes = "application/json")
	PutForwardDto saveAndModify(@RequestBody PutForwardDto dto);
}
