package com.waben.stock.interfaces.service.futures;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.waben.stock.interfaces.dto.futures.FuturesCurrencyRateDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;

@FeignClient(name = "futures", path = "currencyRate", qualifier = "futuresCurrencyRateInterface")
public interface FuturesCurrencyRateInterface {

	/**
	 * 添加期货合约数据
	 * 
	 * @param contractDto
	 * @return
	 */
	@RequestMapping(value = "/saveCurrencyRate", method = RequestMethod.POST, consumes = "application/json")
	Response<FuturesCurrencyRateDto> addCurrencyRate(@RequestBody FuturesCurrencyRateDto dto);

	/**
	 * 修改期货合约数据
	 * 
	 * @param contractDto
	 * @return
	 */
	@RequestMapping(value = "/modifyCurrencyRate", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	Response<FuturesCurrencyRateDto> modifyCurrencyRate(@RequestBody FuturesCurrencyRateDto dto);

	/**
	 * 删除期货合约数据
	 * 
	 * @param id
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	void deleteCurrencyRate(@PathVariable("id") Long id);

	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	Response<PageInfo<FuturesCurrencyRateDto>> list();

	/**
	 * 根据货币查询汇率信息
	 * 
	 * @param currency
	 *            货币
	 * @return 汇率信息
	 */
	@RequestMapping(value = "/rate/{currency}", method = RequestMethod.GET)
	Response<FuturesCurrencyRateDto> findByCurrency(@PathVariable("currency") String currency);

}
