package com.waben.stock.applayer.tactics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.applayer.tactics.business.RealNameBusiness;
import com.waben.stock.applayer.tactics.security.SecurityUtil;
import com.waben.stock.interfaces.dto.publisher.RealNameDto;
import com.waben.stock.interfaces.enums.ResourceType;
import com.waben.stock.interfaces.pojo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 实名认证 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/realname")
@Api(description = "实名认证")
public class RealNameController {

	@Autowired
	private RealNameBusiness business;

	@PostMapping("/")
	@ApiOperation(value = "添加实名认证")
	public Response<RealNameDto> addRealName(@RequestParam(required = true) String name,
			@RequestParam(required = true) String idCard) {
		RealNameDto realName = new RealNameDto();
		realName.setIdCard(idCard);
		realName.setName(name);
		realName.setResourceId(SecurityUtil.getUserId());
		realName.setResourceType(ResourceType.PUBLISHER);
		return new Response<>(business.add(realName));
	}

	@GetMapping("/")
	@ApiOperation(value = "获取实名认证")
	public Response<RealNameDto> fetchRealName() {
		return new Response<>(business.fetch(ResourceType.PUBLISHER, SecurityUtil.getUserId()));
	}

}
