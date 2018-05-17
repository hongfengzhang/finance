package com.waben.stock.applayer.promotion.controller;

import java.util.List;

import com.waben.stock.applayer.promotion.util.SecurityAccount;
import com.waben.stock.interfaces.dto.organization.UserDto;
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

import com.waben.stock.applayer.promotion.business.BindCardBusiness;
import com.waben.stock.applayer.promotion.business.OrganizationBusiness;
import com.waben.stock.interfaces.dto.organization.OrganizationDetailDto;
import com.waben.stock.interfaces.dto.organization.OrganizationDto;
import com.waben.stock.interfaces.dto.organization.TreeNode;
import com.waben.stock.interfaces.dto.publisher.BindCardDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.form.organization.OrganizationForm;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.organization.OrganizationQuery;

import io.swagger.annotations.Api;

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
		UserDto userDto = (UserDto) SecurityAccount.current().getSecurity();
		if(!"admin".equals(userDto.getUsername())){
			query.setParentId(userDto.getOrg().getId());
		}
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
	public Response<OrganizationDetailDto> detail(Long orgId) {
		return new Response<>(business.detail(orgId));
	}

	@RequestMapping(value = "/modifyName", method = RequestMethod.POST)
	public Response<OrganizationDto> modifyName(Long id, String name) {
		return new Response<>(business.modifyName(id, name));
	}

	@RequestMapping(value = "/{orgid}/bindcard", method = RequestMethod.GET)
	public Response<BindCardDto> fetchBindCard(@PathVariable("orgid") Long orgId) {
		return new Response<>(bindCardBusiness.getOrgBindCard(orgId));
	}

	@RequestMapping(value = "/{orgid}/bindcard", method = RequestMethod.POST)
	public Response<BindCardDto> saveBindCard(@PathVariable("orgid") Long orgId, BindCardDto bindCardDto) {
		return new Response<>(bindCardBusiness.orgBindCard(orgId, bindCardDto));
	}

}
