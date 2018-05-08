package com.waben.stock.applayer.promotion.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.google.zxing.WriterException;
import com.waben.stock.applayer.promotion.business.BindCardBusiness;
import com.waben.stock.applayer.promotion.business.OrganizationBusiness;
import com.waben.stock.applayer.promotion.util.QRCodeUtil;
import com.waben.stock.interfaces.dto.organization.AdminAgentDetailDato;
import com.waben.stock.interfaces.dto.organization.OrganizationDetailDto;
import com.waben.stock.interfaces.dto.organization.OrganizationDto;
import com.waben.stock.interfaces.dto.organization.TreeNode;
import com.waben.stock.interfaces.dto.publisher.BindCardDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.form.organization.OrganizationForm;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.organization.OrganizationQuery;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 机构 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/organization")
@Api(description = "机构接口列表")
public class OrganizationController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	public OrganizationBusiness business;

	@Autowired
	public BindCardBusiness bindCardBusiness;

	@PreAuthorize("hasRole('ORG_MANAGE')")
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public Response<OrganizationDto> addition(OrganizationForm orgForm) {
		return new Response<>(business.addition(orgForm));
	}

	@RequestMapping(value = "/adminPage", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Response<PageInfo<OrganizationDto>> adminPage(@RequestBody OrganizationQuery query) {
		return new Response<>(business.adminPage(query));
	}

	@RequestMapping(value = "/pages", method = RequestMethod.POST)
	public Response<PageInfo<OrganizationDto>> pages(@RequestBody OrganizationQuery query) {
		// UserDto userDto = (UserDto) SecurityAccount.current().getSecurity();
		// if(!"admin".equals(userDto.getUsername())){
		// query.setParentId(userDto.getOrg().getId());
		// }
		return new Response<>(business.pages(query));
	}

	@RequestMapping(value = "/adminTree", method = RequestMethod.GET)
	public List<TreeNode> adminTree(Long orgId) {
		return business.adminTree(orgId);
	}

	@RequestMapping(value = "/listByParentId", method = RequestMethod.GET)
	public Response<List<OrganizationDto>> listByParentId(Long parentId) {
		return new Response<>(business.listByParentId(parentId));
	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	@ApiOperation(value = "获取登录平台或代理商账号详细信息")
	public Response<OrganizationDetailDto> detail(Long orgId) {
		return new Response<>(business.detail(orgId));
	}

	@RequestMapping(value = "/modifyName", method = RequestMethod.POST)
	public Response<OrganizationDto> modifyName(Long id, String name) {
		return new Response<>(business.modifyName(id, name));
	}

	@RequestMapping(value = "/{orgid}/bindcard", method = RequestMethod.GET)
	@ApiOperation(value = "获取绑卡信息")
	public Response<BindCardDto> fetchBindCard(@PathVariable("orgid") Long orgId) {
		return new Response<>(bindCardBusiness.getOrgBindCard(orgId));
	}

	@RequestMapping(value = "/{orgid}/bindcard", method = RequestMethod.POST)
	public Response<BindCardDto> saveBindCard(@PathVariable("orgid") Long orgId, BindCardDto bindCardDto) {
		return new Response<>(bindCardBusiness.orgBindCard(orgId, bindCardDto));
	}

	@RequestMapping(value = "/qrcode", method = RequestMethod.GET)
	@ApiOperation(value = "获取推广二维码")
	public Response<OrganizationDetailDto> qrcode(Long orgId) throws IOException, WriterException {
		OrganizationDetailDto dto = business.detail(orgId);
		Map<String, String> contentMap = Maps.newHashMap();
		contentMap.put("name", String.valueOf(dto.getName()));
		contentMap.put("code", String.valueOf(dto.getCode()));
		contentMap.put("state", String.valueOf(dto.getState()));
		String content = JSON.toJSONString(contentMap);
		return new Response<>(QRCodeUtil.create(content, 200, 200));
	}

	@RequestMapping(value = "/adminAgentPage", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "获取代理商列表")
	public Response<PageInfo<AdminAgentDetailDato>> adminAgentPage(@RequestBody OrganizationQuery query) {
		return new Response<>(business.adminAgentPageByQuery(query));
	}

}
