package com.waben.stock.datalayer.promotion.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.datalayer.promotion.pojo.bean.CustomerBean;
import com.waben.stock.datalayer.promotion.pojo.query.CustomerQuery;
import com.waben.stock.datalayer.promotion.service.CustomerService;
import com.waben.stock.interfaces.pojo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 客户 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/customer")
@Api(description = "客户接口列表")
public class CustomerController {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	public CustomerService customerService;
	
	@PostMapping("/adminPage")
	@ApiOperation(value = "获取客户列表(后台管理)")
	public Response<Page<CustomerBean>> adminPage(@RequestBody CustomerQuery query) {
		return new Response<>((Page<CustomerBean>) customerService.pagesByQuery(query));
	}
	
}
