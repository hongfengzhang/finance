package com.waben.stock.interfaces.service.futures;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.waben.stock.interfaces.dto.admin.futures.FuturesPreQuantityDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.admin.futures.FuturesPreQuantityQuery;


@FeignClient(name = "futures", path = "preQuantity", qualifier = "futuresPreQuantityInterface")
public interface FuturesPreQuantityInterface {

	@RequestMapping(value = "/pagesPre", method = RequestMethod.POST, consumes = "application/json")
	Response<PageInfo<FuturesPreQuantityDto>> findAll(@RequestBody FuturesPreQuantityQuery query);
}
