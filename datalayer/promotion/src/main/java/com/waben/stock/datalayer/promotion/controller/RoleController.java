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

import com.waben.stock.datalayer.promotion.entity.Role;
import com.waben.stock.datalayer.promotion.service.RoleService;
import com.waben.stock.interfaces.pojo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 角色 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/role")
@Api(description = "角色接口列表")
public class RoleController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	public RoleService roleService;

	@GetMapping("/{id}")
	@ApiOperation(value = "根据id获取角色")
	public Response<Role> fetchById(@PathVariable Long id) {
		return new Response<>(roleService.getRoleInfo(id));
	}

	@GetMapping("/page")
	@ApiOperation(value = "获取角色分页数据")
	public Response<Page<Role>> roles(int page, int limit) {
		return new Response<>((Page<Role>) roleService.roles(page, limit));
	}
	
	@GetMapping("/list")
	@ApiOperation(value = "获取角色列表")
	public Response<List<Role>> list() {
		return new Response<>(roleService.list());
	}
	
	/******************************** 后台管理 **********************************/
	
	@PostMapping("/")
	@ApiOperation(value = "添加角色", hidden = true)
	public Response<Role> addition(Role role) {
		return new Response<>(roleService.addRole(role));
	}

	@PutMapping("/")
	@ApiOperation(value = "修改角色", hidden = true)
	public Response<Role> modification(Role role) {
		return new Response<>(roleService.modifyRole(role));
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除角色", hidden = true)
	public Response<Long> delete(@PathVariable Long id) {
		roleService.deleteRole(id);
		return new Response<Long>(id);
	}
	
	@PostMapping("/deletes")
	@ApiOperation(value = "批量删除角色（多个id以逗号分割）", hidden = true)
	public Response<Boolean> deletes(String ids) {
		roleService.deleteRoles(ids);
		return new Response<Boolean>(true);
	}
	
	@GetMapping("/adminList")
	@ApiOperation(value = "获取角色列表(后台管理)", hidden = true)
	public Response<List<Role>> adminList() {
		return new Response<>(roleService.list());
	}

}
