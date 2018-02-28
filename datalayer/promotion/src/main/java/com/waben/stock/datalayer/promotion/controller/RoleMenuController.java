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

import com.waben.stock.datalayer.promotion.entity.RoleMenu;
import com.waben.stock.datalayer.promotion.service.RoleMenuService;
import com.waben.stock.interfaces.pojo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 角色菜单 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/roleMenu")
@Api(description = "角色菜单接口列表")
public class RoleMenuController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	public RoleMenuService roleMenuService;

	@GetMapping("/{id}")
	@ApiOperation(value = "根据id获取角色菜单")
	public Response<RoleMenu> fetchById(@PathVariable Long id) {
		return new Response<>(roleMenuService.getRoleMenuInfo(id));
	}

	@GetMapping("/page")
	@ApiOperation(value = "获取角色菜单分页数据")
	public Response<Page<RoleMenu>> roleMenus(int page, int limit) {
		return new Response<>((Page<RoleMenu>) roleMenuService.roleMenus(page, limit));
	}
	
	@GetMapping("/list")
	@ApiOperation(value = "获取角色菜单列表")
	public Response<List<RoleMenu>> list() {
		return new Response<>(roleMenuService.list());
	}
	
	/******************************** 后台管理 **********************************/
	
	@PostMapping("/")
	@ApiOperation(value = "添加角色菜单", hidden = true)
	public Response<RoleMenu> addition(RoleMenu roleMenu) {
		return new Response<>(roleMenuService.addRoleMenu(roleMenu));
	}

	@PutMapping("/")
	@ApiOperation(value = "修改角色菜单", hidden = true)
	public Response<RoleMenu> modification(RoleMenu roleMenu) {
		return new Response<>(roleMenuService.modifyRoleMenu(roleMenu));
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除角色菜单", hidden = true)
	public Response<Long> delete(@PathVariable Long id) {
		roleMenuService.deleteRoleMenu(id);
		return new Response<Long>(id);
	}
	
	@PostMapping("/deletes")
	@ApiOperation(value = "批量删除角色菜单（多个id以逗号分割）", hidden = true)
	public Response<Boolean> deletes(String ids) {
		roleMenuService.deleteRoleMenus(ids);
		return new Response<Boolean>(true);
	}
	
	@GetMapping("/adminList")
	@ApiOperation(value = "获取角色菜单列表(后台管理)", hidden = true)
	public Response<List<RoleMenu>> adminList() {
		return new Response<>(roleMenuService.list());
	}

}
