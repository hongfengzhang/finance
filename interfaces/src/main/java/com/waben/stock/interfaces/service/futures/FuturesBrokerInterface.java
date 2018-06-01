package com.waben.stock.interfaces.service.futures;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.waben.stock.interfaces.dto.futures.FuturesBrokerDto;
import com.waben.stock.interfaces.pojo.Response;

@FeignClient(name = "futures", path = "broker", qualifier = "futuresBrokerInterface")
public interface FuturesBrokerInterface {

	@RequestMapping(value = "/borker/{brokerId}")
	Response<FuturesBrokerDto> findByrokerId(@PathVariable("brokerId") Long brokerId);
}
