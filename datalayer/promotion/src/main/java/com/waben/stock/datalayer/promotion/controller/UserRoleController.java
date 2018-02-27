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

import com.waben.stock.datalayer.promotion.entity.UserRole;
import com.waben.stock.datalayer.promotion.service.UserRoleService;
import com.waben.stock.interfaces.pojo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 用户角色 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/userRole")
@Api(description = "用户角色接口列表")
public class UserRoleController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	public UserRoleService userRoleService;

	@GetMapping("/{id}")
	@ApiOperation(value = "根据id获取用户角色")
	public Response<UserRole> fetchById(@PathVariable Long id) {
		return new Response<>(userRoleService.getUserRoleInfo(id));
	}

	@GetMapping("/page")
	@ApiOperation(value = "获取用户角色分页数据")
	public Response<Page<UserRole>> userRoles(int page, int limit) {
		return new Response<>((Page<UserRole>) userRoleService.userRoles(page, limit));
	}
	
	@GetMapping("/list")
	@ApiOperation(value = "获取用户角色列表")
	public Response<List<UserRole>> list() {
		return new Response<>(userRoleService.list());
	}
	
	/******************************** 后台管理 **********************************/
	
	@PostMapping("/")
	@ApiOperation(value = "添加用户角色", hidden = true)
	public Response<UserRole> addition(UserRole userRole) {
		return new Response<>(userRoleService.addUserRole(userRole));
	}

	@PutMapping("/")
	@ApiOperation(value = "修改用户角色", hidden = true)
	public Response<UserRole> modification(UserRole userRole) {
		return new Response<>(userRoleService.modifyUserRole(userRole));
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除用户角色", hidden = true)
	public Response<Long> delete(@PathVariable Long id) {
		userRoleService.deleteUserRole(id);
		return new Response<Long>(id);
	}
	
	@PostMapping("/deletes")
	@ApiOperation(value = "批量删除用户角色（多个id以逗号分割）", hidden = true)
	public Response<Boolean> deletes(String ids) {
		userRoleService.deleteUserRoles(ids);
		return new Response<Boolean>(true);
	}
	
	@GetMapping("/adminList")
	@ApiOperation(value = "获取用户角色列表(后台管理)", hidden = true)
	public Response<List<UserRole>> adminList() {
		return new Response<>(userRoleService.list());
	}

}
