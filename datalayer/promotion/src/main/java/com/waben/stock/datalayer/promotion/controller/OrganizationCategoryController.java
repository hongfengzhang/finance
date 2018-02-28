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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.datalayer.promotion.entity.OrganizationCategory;
import com.waben.stock.datalayer.promotion.service.OrganizationCategoryService;
import com.waben.stock.interfaces.pojo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 机构类别 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/organizationCategory")
@Api(description = "机构类别接口列表")
public class OrganizationCategoryController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	public OrganizationCategoryService organizationCategoryService;

	@GetMapping("/{id}")
	@ApiOperation(value = "根据id获取机构类别")
	public Response<OrganizationCategory> fetchById(@PathVariable Long id) {
		return new Response<>(organizationCategoryService.getOrganizationCategoryInfo(id));
	}

	@GetMapping("/page")
	@ApiOperation(value = "获取机构类别分页数据")
	public Response<Page<OrganizationCategory>> organizationCategorys(int page, int limit) {
		return new Response<>((Page<OrganizationCategory>) organizationCategoryService.organizationCategorys(page, limit));
	}
	
	@GetMapping("/list")
	@ApiOperation(value = "获取机构类别列表")
	public Response<List<OrganizationCategory>> list() {
		return new Response<>(organizationCategoryService.list());
	}
	
	/******************************** 后台管理 **********************************/
	
	@PostMapping("/")
	@ApiOperation(value = "添加机构类别", hidden = true)
	public Response<OrganizationCategory> addition(OrganizationCategory organizationCategory) {
		return new Response<>(organizationCategoryService.addOrganizationCategory(organizationCategory));
	}

	@PutMapping("/")
	@ApiOperation(value = "修改机构类别", hidden = true)
	public Response<OrganizationCategory> modification(OrganizationCategory organizationCategory) {
		return new Response<>(organizationCategoryService.modifyOrganizationCategory(organizationCategory));
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除机构类别", hidden = true)
	public Response<Long> delete(@PathVariable Long id) {
		organizationCategoryService.deleteOrganizationCategory(id);
		return new Response<Long>(id);
	}
	
	@PostMapping("/deletes")
	@ApiOperation(value = "批量删除机构类别（多个id以逗号分割）", hidden = true)
	public Response<Boolean> deletes(String ids) {
		organizationCategoryService.deleteOrganizationCategorys(ids);
		return new Response<Boolean>(true);
	}
	
	@GetMapping("/adminList")
	@ApiOperation(value = "获取机构类别列表(后台管理)", hidden = true)
	public Response<List<OrganizationCategory>> adminList() {
		return new Response<>(organizationCategoryService.list());
	}

}
