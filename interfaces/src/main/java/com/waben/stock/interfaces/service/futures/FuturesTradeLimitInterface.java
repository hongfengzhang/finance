package com.waben.stock.interfaces.service.futures;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.waben.stock.interfaces.dto.admin.futures.FuturesTradeLimitDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.admin.futures.FuturesTradeLimitQuery;

@FeignClient(name = "futures", path = "tradeLimit", qualifier = "futuresTradeLimitInterface")
public interface FuturesTradeLimitInterface {

	@RequestMapping(value = "/addLimit", method = RequestMethod.POST, consumes = "application/json")
	Response<FuturesTradeLimitDto> addLimit(@RequestBody FuturesTradeLimitDto query);
	
	@RequestMapping(value = "/modifyLimit", method = RequestMethod.POST, consumes = "application/json")
	Response<FuturesTradeLimitDto> modifyLimit(@RequestBody FuturesTradeLimitDto query);
	
	@RequestMapping(value = "/tradeLimit/delete/{id}", method = RequestMethod.GET)
	void deleteLimit(@PathVariable("id") Long id);
	
	@RequestMapping(value = "/pagesLimit", method = RequestMethod.POST, consumes = "application/json")
	Response<PageInfo<FuturesTradeLimitDto>> pagesLimit(@RequestBody FuturesTradeLimitQuery query);
}
