package com.waben.stock.interfaces.service.futures;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.waben.stock.interfaces.dto.futures.FuturesExchangeDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.admin.futures.FuturesExchangeAdminQuery;

@FeignClient(name = "futures", path = "exchange", qualifier = "futuresExchangeInterface")
public interface FuturesExchangeInterface {

	/**
	 * 查询期货市场
	 * 
	 * @param exchangeQuery
	 * @return
	 * @throws Throwable
	 */
	@RequestMapping(value = "/pagesExchange", method = RequestMethod.POST, consumes = "application/json")
	Response<PageInfo<FuturesExchangeDto>> pagesExchange(@RequestBody FuturesExchangeAdminQuery exchangeQuery);

	/**
	 * 添加期货市场
	 * 
	 * @param exchangeQuery
	 * @return
	 * @throws Throwable
	 */
	@RequestMapping(value = "/addExchange", method = RequestMethod.POST, consumes = "application/json")
	Response<FuturesExchangeDto> addExchange(@RequestBody FuturesExchangeDto query);

	/**
	 * 修改期货市场
	 * 
	 * @param exchangeQuery
	 * @return
	 * @throws Throwable
	 */
	@RequestMapping(value = "/modifyExchange", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	Response<FuturesExchangeDto> modifyExchange(@RequestBody FuturesExchangeDto exchangeDto);

	/**
	 * 删除期货市场
	 * 
	 * @param exchangeQuery
	 * @return
	 * @throws Throwable
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	void deleteExchange(@PathVariable("id") Long id);

	/**
	 * 根据ID获取交易所信息
	 * 
	 * @param exchangeId
	 *            交易所ID
	 * @return 交易所信息
	 */
	@RequestMapping(value = "/exchange/{exchangeId}", method = RequestMethod.GET)
	Response<FuturesExchangeDto> findByExchangeId(@PathVariable("exchangeId") Long exchangeId);
}
