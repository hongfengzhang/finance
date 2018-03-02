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

import com.waben.stock.datalayer.promotion.entity.Menu;
import com.waben.stock.datalayer.promotion.service.MenuService;
import com.waben.stock.interfaces.pojo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 菜单 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/menu")
@Api(description = "菜单接口列表")
public class MenuController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	public MenuService menuService;

	@GetMapping("/{id}")
	@ApiOperation(value = "根据id获取菜单")
	public Response<Menu> fetchById(@PathVariable Long id) {
		return new Response<>(menuService.getMenuInfo(id));
	}

	@GetMapping("/page")
	@ApiOperation(value = "获取菜单分页数据")
	public Response<Page<Menu>> menus(int page, int limit) {
		return new Response<>((Page<Menu>) menuService.menus(page, limit));
	}
	
	@GetMapping("/list")
	@ApiOperation(value = "获取菜单列表")
	public Response<List<Menu>> list() {
		return new Response<>(menuService.list());
	}
	
	/******************************** 后台管理 **********************************/
	
	@PostMapping("/")
	@ApiOperation(value = "添加菜单", hidden = true)
	public Response<Menu> addition(Menu menu) {
		return new Response<>(menuService.addMenu(menu));
	}

	@PutMapping("/")
	@ApiOperation(value = "修改菜单", hidden = true)
	public Response<Menu> modification(Menu menu) {
		return new Response<>(menuService.modifyMenu(menu));
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除菜单", hidden = true)
	public Response<Long> delete(@PathVariable Long id) {
		menuService.deleteMenu(id);
		return new Response<Long>(id);
	}
	
	@PostMapping("/deletes")
	@ApiOperation(value = "批量删除菜单（多个id以逗号分割）", hidden = true)
	public Response<Boolean> deletes(String ids) {
		menuService.deleteMenus(ids);
		return new Response<Boolean>(true);
	}
	
	@GetMapping("/adminList")
	@ApiOperation(value = "获取菜单列表(后台管理)", hidden = true)
	public Response<List<Menu>> adminList() {
		return new Response<>(menuService.list());
	}

}
