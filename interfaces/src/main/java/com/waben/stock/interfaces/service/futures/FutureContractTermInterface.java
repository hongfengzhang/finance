package com.waben.stock.interfaces.service.futures;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.waben.stock.interfaces.dto.admin.futures.FuturesTermAdminDto;
import com.waben.stock.interfaces.pojo.Response;

@FeignClient(name = "futures", path = "contractTerm", qualifier = "futureContractTermInterface")
public interface FutureContractTermInterface {

	
	/**
	 * 添加期货合约数据
	 * @param contractDto
	 * @return
	 */
	@RequestMapping(value = "/saveContractTerm", method = RequestMethod.POST,consumes = "application/json")
	Response<FuturesTermAdminDto> addContractTerm(@RequestBody FuturesTermAdminDto dto);
	
	/**
	 * 修改期货合约数据
	 * @param contractDto
	 * @return
	 */
	@RequestMapping(value = "/modifyContractTerm", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	Response<FuturesTermAdminDto> modifyContractTerm(@RequestBody FuturesTermAdminDto dto);
	
	/**
	 * 删除期货合约数据
	 * @param id
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	void deleteContractTerm(@PathVariable("id") Long id);
}
