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

import com.waben.stock.datalayer.promotion.entity.Agent;
import com.waben.stock.datalayer.promotion.service.AgentService;
import com.waben.stock.interfaces.pojo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 经纪人 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/agent")
@Api(description = "经纪人接口列表")
public class AgentController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	public AgentService agentService;

	@GetMapping("/{id}")
	@ApiOperation(value = "根据id获取经纪人")
	public Response<Agent> fetchById(@PathVariable Long id) {
		return new Response<>(agentService.getAgentInfo(id));
	}

	@GetMapping("/page")
	@ApiOperation(value = "获取经纪人分页数据")
	public Response<Page<Agent>> agents(int page, int limit) {
		return new Response<>((Page<Agent>) agentService.agents(page, limit));
	}
	
	@GetMapping("/list")
	@ApiOperation(value = "获取经纪人列表")
	public Response<List<Agent>> list() {
		return new Response<>(agentService.list());
	}
	
	/******************************** 后台管理 **********************************/
	
	@PostMapping("/")
	@ApiOperation(value = "添加经纪人", hidden = true)
	public Response<Agent> addition(Agent agent) {
		return new Response<>(agentService.addAgent(agent));
	}

	@PutMapping("/")
	@ApiOperation(value = "修改经纪人", hidden = true)
	public Response<Agent> modification(Agent agent) {
		return new Response<>(agentService.modifyAgent(agent));
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除经纪人", hidden = true)
	public Response<Long> delete(@PathVariable Long id) {
		agentService.deleteAgent(id);
		return new Response<Long>(id);
	}
	
	@PostMapping("/deletes")
	@ApiOperation(value = "批量删除经纪人（多个id以逗号分割）", hidden = true)
	public Response<Boolean> deletes(String ids) {
		agentService.deleteAgents(ids);
		return new Response<Boolean>(true);
	}
	
	@GetMapping("/adminList")
	@ApiOperation(value = "获取经纪人列表(后台管理)", hidden = true)
	public Response<List<Agent>> adminList() {
		return new Response<>(agentService.list());
	}

}
