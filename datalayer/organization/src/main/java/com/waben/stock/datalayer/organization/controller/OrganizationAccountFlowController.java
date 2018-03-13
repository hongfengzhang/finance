package com.waben.stock.datalayer.organization.controller;

import java.util.List;

import com.waben.stock.interfaces.dto.organization.OrganizationAccountFlowDto;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.organization.OrganizationAccountFlowQuery;
import com.waben.stock.interfaces.service.organization.OrganizationAccountFlowInterface;
import com.waben.stock.interfaces.util.PageToPageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.waben.stock.datalayer.organization.entity.OrganizationAccountFlow;
import com.waben.stock.datalayer.organization.service.OrganizationAccountFlowService;
import com.waben.stock.interfaces.pojo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 机构账户流水 Controller
 *
 * @author luomengan
 */
@RestController
@RequestMapping("/organizationAccountFlow")
@Api(description = "机构账户流水接口列表")
public class OrganizationAccountFlowController implements OrganizationAccountFlowInterface {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public OrganizationAccountFlowService organizationAccountFlowService;

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id获取机构账户流水")
    public Response<OrganizationAccountFlow> fetchById(@PathVariable Long id) {
        return new Response<>(organizationAccountFlowService.getOrganizationAccountFlowInfo(id));
    }

    @GetMapping("/page")
    @ApiOperation(value = "获取机构账户流水分页数据")
    public Response<Page<OrganizationAccountFlow>> organizationAccountFlows(int page, int limit) {
        return new Response<>((Page<OrganizationAccountFlow>) organizationAccountFlowService.organizationAccountFlows(page, limit));
    }

    @GetMapping("/list")
    @ApiOperation(value = "获取机构账户流水列表")
    public Response<List<OrganizationAccountFlow>> list() {
        return new Response<>(organizationAccountFlowService.list());
    }

    /**
     *按照查询条件分页查询机构流水
    */
    @Override
    public Response<PageInfo<OrganizationAccountFlowDto>> pages(@RequestBody OrganizationAccountFlowQuery query) {
        Page<OrganizationAccountFlow> page = organizationAccountFlowService.pagesByQuery(query);
        PageInfo<OrganizationAccountFlowDto> result = PageToPageInfo.pageToPageInfo(page, OrganizationAccountFlowDto.class);
        return new Response<>(result);
    }

    @Override
    public Response<PageInfo<OrganizationAccountFlowDto>> childpages(OrganizationAccountFlowQuery query) {
        return null;
    }

    /******************************** 后台管理 **********************************/

    @PostMapping("/")
    @ApiOperation(value = "添加机构账户流水", hidden = true)
    public Response<OrganizationAccountFlow> addition(OrganizationAccountFlow organizationAccountFlow) {
        return new Response<>(organizationAccountFlowService.addOrganizationAccountFlow(organizationAccountFlow));
    }

    @PutMapping("/")
    @ApiOperation(value = "修改机构账户流水", hidden = true)
    public Response<OrganizationAccountFlow> modification(OrganizationAccountFlow organizationAccountFlow) {
        return new Response<>(organizationAccountFlowService.modifyOrganizationAccountFlow(organizationAccountFlow));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除机构账户流水", hidden = true)
    public Response<Long> delete(@PathVariable Long id) {
        organizationAccountFlowService.deleteOrganizationAccountFlow(id);
        return new Response<Long>(id);
    }

    @PostMapping("/deletes")
    @ApiOperation(value = "批量删除机构账户流水（多个id以逗号分割）", hidden = true)
    public Response<Boolean> deletes(String ids) {
        organizationAccountFlowService.deleteOrganizationAccountFlows(ids);
        return new Response<Boolean>(true);
    }

    @GetMapping("/adminList")
    @ApiOperation(value = "获取机构账户流水列表(后台管理)", hidden = true)
    public Response<List<OrganizationAccountFlow>> adminList() {
        return new Response<>(organizationAccountFlowService.list());
    }
}
