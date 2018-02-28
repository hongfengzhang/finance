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

import com.waben.stock.datalayer.promotion.entity.RealName;
import com.waben.stock.datalayer.promotion.service.RealNameService;
import com.waben.stock.interfaces.pojo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 实名信息 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/realName")
@Api(description = "实名信息接口列表")
public class RealNameController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	public RealNameService realNameService;

	@GetMapping("/{id}")
	@ApiOperation(value = "根据id获取实名信息")
	public Response<RealName> fetchById(@PathVariable Long id) {
		return new Response<>(realNameService.getRealNameInfo(id));
	}

	@GetMapping("/page")
	@ApiOperation(value = "获取实名信息分页数据")
	public Response<Page<RealName>> realNames(int page, int limit) {
		return new Response<>((Page<RealName>) realNameService.realNames(page, limit));
	}
	
	@GetMapping("/list")
	@ApiOperation(value = "获取实名信息列表")
	public Response<List<RealName>> list() {
		return new Response<>(realNameService.list());
	}
	
	/******************************** 后台管理 **********************************/
	
	@PostMapping("/")
	@ApiOperation(value = "添加实名信息", hidden = true)
	public Response<RealName> addition(RealName realName) {
		return new Response<>(realNameService.addRealName(realName));
	}

	@PutMapping("/")
	@ApiOperation(value = "修改实名信息", hidden = true)
	public Response<RealName> modification(RealName realName) {
		return new Response<>(realNameService.modifyRealName(realName));
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除实名信息", hidden = true)
	public Response<Long> delete(@PathVariable Long id) {
		realNameService.deleteRealName(id);
		return new Response<Long>(id);
	}
	
	@PostMapping("/deletes")
	@ApiOperation(value = "批量删除实名信息（多个id以逗号分割）", hidden = true)
	public Response<Boolean> deletes(String ids) {
		realNameService.deleteRealNames(ids);
		return new Response<Boolean>(true);
	}
	
	@GetMapping("/adminList")
	@ApiOperation(value = "获取实名信息列表(后台管理)", hidden = true)
	public Response<List<RealName>> adminList() {
		return new Response<>(realNameService.list());
	}

}
