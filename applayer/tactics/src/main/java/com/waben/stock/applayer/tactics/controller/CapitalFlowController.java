package com.waben.stock.applayer.tactics.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.applayer.tactics.business.CapitalFlowBusiness;
import com.waben.stock.applayer.tactics.dto.publisher.CapitalFlowWithExtendDto;
import com.waben.stock.applayer.tactics.security.SecurityUtil;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.CapitalFlowQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 资金流水 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/capitalFlow")
@Api(description = "资金流水")
public class CapitalFlowController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CapitalFlowBusiness capitalFlowBusiness;

	@GetMapping("/pages")
	@ApiOperation(value = "用户资金流水")
	public Response<PageInfo<CapitalFlowWithExtendDto>> publisherCapitalFlow(int page, int size) {
		CapitalFlowQuery query = new CapitalFlowQuery(page, size);
		query.setPublisherId(SecurityUtil.getUserId());
		return new Response<>(capitalFlowBusiness.pages(query));
	}

}
