package com.waben.stock.datalayer.organization.controller;

import java.util.List;

import com.waben.stock.interfaces.dto.organization.UserDto;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.service.organization.UserInterface;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.util.PageToPageInfo;
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

import com.waben.stock.datalayer.organization.entity.User;
import com.waben.stock.datalayer.organization.service.UserService;
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
public class UserController implements UserInterface {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	public UserService userService;

	@GetMapping("/{id}")
	@ApiOperation(value = "根据id获取机构管理用户")
	public Response<UserDto> fetchById(@PathVariable Long id) {
		User result = userService.getUserInfo(id);
		UserDto resposne = CopyBeanUtils.copyBeanProperties(UserDto.class, result, false);
		return new Response<>(resposne);
	}

	@GetMapping("/page")
	@ApiOperation(value = "获取机构管理用户分页数据")
	public Response<PageInfo<UserDto>> users(int page, int limit) {
		Page<User> result = userService.users(page, limit);
		PageInfo<UserDto> response = PageToPageInfo.pageToPageInfo(result, UserDto.class);
		return new Response<>(response);
	}
	
	@GetMapping("/list")
	@ApiOperation(value = "获取机构管理用户列表")
	public Response<List<UserDto>> list() {
		List<User> result = userService.list();
		List<UserDto> response = CopyBeanUtils.copyListBeanPropertiesToList(result, UserDto.class);
		return new Response<>(response);
	}
	
	/******************************** 后台管理 **********************************/
	
	@PostMapping("/")
	@ApiOperation(value = "添加机构管理用户", hidden = true)
	public Response<UserDto> addition(UserDto user) {
		User request = CopyBeanUtils.copyBeanProperties(user, new User(), false);
		User result = userService.addUser(request);
		UserDto response = CopyBeanUtils.copyBeanProperties(UserDto.class, result, false);
		return new Response<>(response);
	}

	@PutMapping("/")
	@ApiOperation(value = "修改机构管理用户", hidden = true)
	public Response<UserDto> modification(UserDto user) {
		User request = CopyBeanUtils.copyBeanProperties(user, new User(), false);
		User result = userService.addUser(request);
		UserDto response = CopyBeanUtils.copyBeanProperties(result, new UserDto(), false);
		return new Response<>(response);
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除机构管理用户", hidden = true)
	public Response<Long> delete(@PathVariable Long id) {
		userService.deleteUser(id);
		return new Response<>(id);
	}
	
	@PostMapping("/deletes")
	@ApiOperation(value = "批量删除机构管理用户（多个id以逗号分割）", hidden = true)
	public Response<Boolean> deletes(String ids) {
		userService.deleteUsers(ids);
		return new Response<>(true);
	}
	
	@GetMapping("/adminList")
	@ApiOperation(value = "获取机构管理用户列表(后台管理)", hidden = true)
	public Response<List<UserDto>> adminList() {
		List<User> result = userService.list();
		List<UserDto> response = CopyBeanUtils.copyListBeanPropertiesToList(result, UserDto.class);
		return new Response<>(response);
	}

	@Override
	public Response<UserDto> fetchByUserName(String userName) {
		User user = userService.findByUserName(userName);
		UserDto response = CopyBeanUtils.copyBeanProperties(user, new UserDto(), false);
		return new Response<>(response);
	}

}
