package com.waben.stock.applayer.admin.controller;

import com.waben.stock.interfaces.dto.publisher.PublisherDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.waben.stock.applayer.admin.business.PublisherBusiness;
import com.waben.stock.interfaces.dto.admin.publisher.PublisherAdminDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.admin.publisher.PublisherAdminQuery;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 发布人 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/publisher")
@Api(description = "发布人")
public class PublisherController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PublisherBusiness business;

	@GetMapping("/pages")
	@ApiOperation(value = "查询发布人")
	public Response<PageInfo<PublisherAdminDto>> pages(PublisherAdminQuery query) {
		return new Response<>(business.adminPagesByQuery(query));
	}

	@PostMapping("/defriend/{id}")
	public Response<PublisherDto> defriend(@PathVariable Long id) {
		PublisherDto response = business.defriend(id);
		return new Response<>(response);
	}

	@PostMapping("/recover/{id}")
	public Response<PublisherDto> recover(@PathVariable Long id) {
		PublisherDto response = business.recover(id);
		return new Response<>(response);
	}
}
