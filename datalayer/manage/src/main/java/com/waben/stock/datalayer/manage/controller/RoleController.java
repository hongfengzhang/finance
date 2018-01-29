package com.waben.stock.datalayer.manage.controller;

import com.waben.stock.datalayer.manage.entity.Role;
import com.waben.stock.datalayer.manage.entity.Staff;
import com.waben.stock.datalayer.manage.service.RoleService;
import com.waben.stock.interfaces.dto.manage.RoleDto;
import com.waben.stock.interfaces.dto.manage.StaffDto;
import com.waben.stock.interfaces.dto.stockcontent.StrategyTypeDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.RoleQuery;
import com.waben.stock.interfaces.service.manage.RoleInterface;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.util.PageToPageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Created by yuyidi on 2017/11/16.
 * @desc
 */
@RestController
@RequestMapping("/role")
public class RoleController implements RoleInterface {

    @Autowired
    private RoleService roleService;

    @Override
    public Response<RoleDto> role(@PathVariable Long id) {
        Role role = roleService.findById(id);
        RoleDto result = CopyBeanUtils.copyBeanProperties(role, new RoleDto(), false);
        return new Response<>(result);
    }

    @Override
    public Response<PageInfo<RoleDto>> pages(@RequestBody RoleQuery query) {
        Page<Role> page = roleService.pagesByQuery(query);
        PageInfo<RoleDto> result = PageToPageInfo.pageToPageInfo(page, RoleDto.class);
        return new Response<>(result);
    }

    @Override
    public Response<RoleDto> fetchById(@PathVariable Long id) {
        Role role = roleService.fetchById(id);
        RoleDto roleDto = CopyBeanUtils.copyBeanProperties(role, new RoleDto(), false);
        return new Response<>(roleDto);
    }

    @Override
    public Response<RoleDto> modify(@RequestBody  RoleDto roleDto) {
        Role role = CopyBeanUtils.copyBeanProperties(Role.class, roleDto, false);
        RoleDto result = CopyBeanUtils.copyBeanProperties(RoleDto.class,roleService.revision(role),false);
        return new Response<>(result);
    }

    @Override
    public void delete(@PathVariable Long id) {
        roleService.delete(id);
    }
}
