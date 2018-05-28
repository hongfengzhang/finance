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

	/**
	 * 查询期货合约数据
	 * 
	 * @param futuresContractQuery
	 *            查询条件
	 * @return 期货合约
	 */
	@RequestMapping(value = "/pages", method = RequestMethod.POST, consumes = "application/json")
	Response<PageInfo<FuturesContractDto>> pagesContract(@RequestBody FuturesContractQuery futuresContractQuery);


}
