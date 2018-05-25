package com.waben.stock.interfaces.service.futures;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.waben.stock.interfaces.dto.futures.FuturesContractDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.futures.FuturesContractQuery;

@FeignClient(name = "futurescontract", path = "futurescontract", qualifier = "futurescontractInterface")
public interface FuturesContractInterface {

	@RequestMapping(value = "/pages", method = RequestMethod.GET, consumes = "application/json")
	Response<PageInfo<FuturesContractDto>> pagesByQuery(@RequestBody FuturesContractQuery lossQuery);
}
