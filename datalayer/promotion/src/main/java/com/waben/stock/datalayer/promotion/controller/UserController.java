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

import com.waben.stock.datalayer.promotion.entity.User;
import com.waben.stock.datalayer.promotion.service.UserService;
import com.waben.stock.interfaces.pojo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 机构管理用户 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/user")
@Api(description = "机构管理用户接口列表")
public class UserController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	public UserService userService;

	@GetMapping("/{id}")
	@ApiOperation(value = "根据id获取机构管理用户")
	public Response<User> fetchById(@PathVariable Long id) {
		return new Response<>(userService.getUserInfo(id));
	}

	@GetMapping("/page")
	@ApiOperation(value = "获取机构管理用户分页数据")
	public Response<Page<User>> users(int page, int limit) {
		return new Response<>((Page<User>) userService.users(page, limit));
	}
	
	@GetMapping("/list")
	@ApiOperation(value = "获取机构管理用户列表")
	public Response<List<User>> list() {
		return new Response<>(userService.list());
	}
	
	/******************************** 后台管理 **********************************/
	
	@PostMapping("/")
	@ApiOperation(value = "添加机构管理用户", hidden = true)
	public Response<User> addition(User user) {
		return new Response<>(userService.addUser(user));
	}

	@PutMapping("/")
	@ApiOperation(value = "修改机构管理用户", hidden = true)
	public Response<User> modification(User user) {
		return new Response<>(userService.modifyUser(user));
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除机构管理用户", hidden = true)
	public Response<Long> delete(@PathVariable Long id) {
		userService.deleteUser(id);
		return new Response<Long>(id);
	}
	
	@PostMapping("/deletes")
	@ApiOperation(value = "批量删除机构管理用户（多个id以逗号分割）", hidden = true)
	public Response<Boolean> deletes(String ids) {
		userService.deleteUsers(ids);
		return new Response<Boolean>(true);
	}
	
	@GetMapping("/adminList")
	@ApiOperation(value = "获取机构管理用户列表(后台管理)", hidden = true)
	public Response<List<User>> adminList() {
		return new Response<>(userService.list());
	}

}
