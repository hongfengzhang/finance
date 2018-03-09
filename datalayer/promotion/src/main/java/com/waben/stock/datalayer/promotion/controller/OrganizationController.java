package com.waben.stock.datalayer.promotion.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.datalayer.promotion.entity.Organization;
import com.waben.stock.datalayer.promotion.pojo.bean.OrganizationDetailBean;
import com.waben.stock.datalayer.promotion.pojo.bean.TreeNode;
import com.waben.stock.datalayer.promotion.pojo.form.OrganizationForm;
import com.waben.stock.datalayer.promotion.pojo.query.OrganizationQuery;
import com.waben.stock.datalayer.promotion.service.OrganizationService;
import com.waben.stock.interfaces.dto.publisher.BindCardDto;
import com.waben.stock.interfaces.pojo.Response;

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
	public OrganizationService organizationService;

	@GetMapping("/{id}")
	@ApiOperation(value = "根据id获取机构")
	public Response<Organization> fetchById(@PathVariable Long id) {
		return new Response<>(organizationService.getOrganizationInfo(id));
	}

	@GetMapping("/page")
	@ApiOperation(value = "获取机构分页数据")
	public Response<Page<Organization>> organizations(int page, int limit) {
		return new Response<>((Page<Organization>) organizationService.organizations(page, limit));
	}

	@GetMapping("/list")
	@ApiOperation(value = "获取机构列表")
	public Response<List<Organization>> list() {
		return new Response<>(organizationService.list());
	}

	/******************************** 后台管理 **********************************/

	@PostMapping("/")
	@ApiOperation(value = "添加机构", hidden = true)
	public Response<Organization> addition(OrganizationForm orgForm) {
		return new Response<>(organizationService.addOrganization(orgForm));
	}

	@PutMapping("/")
	@ApiOperation(value = "修改机构", hidden = true)
	public Response<Organization> modification(Organization organization) {
		return new Response<>(organizationService.modifyOrganization(organization));
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除机构", hidden = true)
	public Response<Long> delete(@PathVariable Long id) {
		organizationService.deleteOrganization(id);
		return new Response<Long>(id);
	}

	@PostMapping("/deletes")
	@ApiOperation(value = "批量删除机构（多个id以逗号分割）", hidden = true)
	public Response<Boolean> deletes(String ids) {
		organizationService.deleteOrganizations(ids);
		return new Response<Boolean>(true);
	}

	@GetMapping("/adminList")
	@ApiOperation(value = "获取机构列表(后台管理)", hidden = true)
	public Response<List<Organization>> adminList() {
		return new Response<>(organizationService.list());
	}

	@PostMapping("/adminPage")
	@ApiOperation(value = "获取机构分页数据(后台管理)")
	public Response<Page<Organization>> adminPage(@RequestBody OrganizationQuery query) {
		return new Response<>((Page<Organization>) organizationService.pagesByQuery(query));
	}

	@GetMapping("/adminTree")
	@ApiOperation(value = "获取机构树")
	public List<TreeNode> adminTree(@RequestParam(required = true) Long orgId) {
		return organizationService.adminTree(orgId);
	}

	@GetMapping("/listByParentId")
	@ApiOperation(value = "根据父机构ID获取机构")
	public Response<List<Organization>> listByParentId(
			@RequestParam(required = true, defaultValue = "0") Long parentId) {
		return new Response<>(organizationService.listByParentId(parentId));
	}

	@GetMapping("/detail")
	@ApiOperation(value = "获取机构详情")
	public Response<OrganizationDetailBean> detail(@RequestParam(required = true) Long orgId) {
		return new Response<>(organizationService.detail(orgId));
	}

	@PostMapping("/modifyName")
	@ApiOperation(value = "修改机构名称")
	public Response<Organization> modifyName(@RequestParam(required = true) Long id,
			@RequestParam(required = true) String name) {
		return new Response<>(organizationService.modifyName(id, name));
	}

	@GetMapping("/{orgid}/bindcard")
	@ApiOperation(value = "获取机构绑卡信息")
	public Response<BindCardDto> fetchBindCard(@PathVariable("orgid") Long orgId) {
		return new Response<>(organizationService.getBindCard(orgId));
	}

	@PostMapping("/{orgid}/bindcard")
	@ApiOperation(value = "保存机构绑卡信息")
	public Response<BindCardDto> saveBindCard(@PathVariable("orgid") Long orgId, BindCardDto bindCardDto) {
		return new Response<>(organizationService.bindCard(orgId, bindCardDto));
	}

}
