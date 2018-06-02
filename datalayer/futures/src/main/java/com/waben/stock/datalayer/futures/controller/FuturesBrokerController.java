package com.waben.stock.datalayer.futures.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.datalayer.futures.service.FuturesBrokerService;
import com.waben.stock.interfaces.dto.futures.FuturesBrokerDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.service.futures.FuturesBrokerInterface;
import com.waben.stock.interfaces.util.CopyBeanUtils;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/broker")
@Api(description = "期货券商接口列表")
public class FuturesBrokerController implements FuturesBrokerInterface {

	@Autowired
	private FuturesBrokerService futuresBrokerService;

	@Override
	public Response<FuturesBrokerDto> findByrokerId(@PathVariable Long brokerId) {
		return new Response<>(CopyBeanUtils.copyBeanProperties(FuturesBrokerDto.class,
				futuresBrokerService.findByBrokerId(brokerId), false));
	}

}
