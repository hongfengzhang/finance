package com.waben.stock.interfaces.service.futures;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.waben.stock.interfaces.dto.futures.FuturesOrderDto;
import com.waben.stock.interfaces.enums.FuturesOrderState;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.futures.FuturesOrderQuery;

@FeignClient(name = "futuresorder", path = "futuresorder", qualifier = "futuresOrderInterface")
public interface FuturesOrderInterface {

	/**
	 * 查询期货订单数据
	 * 
	 * @param orderQuery
	 *            查询条件
	 * @return 期货订单
	 */
	@RequestMapping(value = "/pages", method = RequestMethod.POST, consumes = "application/json")
	Response<PageInfo<FuturesOrderDto>> pagesOrder(@RequestBody FuturesOrderQuery orderQuery);

	/**
	 * 添加期货订单
	 * 
	 * @param futuresOrderDto
	 *            订单数据
	 * @return 期货订单
	 */
	@RequestMapping(value = "/addOrder", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	Response<FuturesOrderDto> addOrder(@RequestBody FuturesOrderDto futuresOrderDto);

	/**
	 * 修改期货订单
	 * 
	 * @param id
	 *            订单id
	 * @param state
	 *            订单状态
	 * @return 期货订单
	 */
	@RequestMapping(value = "/editOrder/{id}", method = RequestMethod.POST)
	Response<FuturesOrderDto> editOrder(@PathVariable("id") Long id,
			@RequestParam(name = "state") FuturesOrderState state);

}
