package com.waben.stock.datalayer.manage.controller;

import com.waben.stock.datalayer.manage.entity.Permission;
import com.waben.stock.datalayer.manage.entity.Role;
import com.waben.stock.datalayer.manage.service.PermissionService;
import com.waben.stock.interfaces.dto.manage.PermissionDto;
import com.waben.stock.interfaces.dto.manage.RoleDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.PermissionQuery;
import com.waben.stock.interfaces.service.manage.PermissionInterface;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.util.JacksonUtil;
import com.waben.stock.interfaces.util.PageToPageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Created by yuyidi on 2017/12/11.
 * @desc
 */
@RestController
@RequestMapping("/permission")
public class PermissionController implements PermissionInterface {

    @Autowired
    private PermissionService permissionService;

    @Override
    public Response<PermissionDto> permission(@PathVariable Long id) {
        Permission permission = permissionService.findById(id);
        PermissionDto result = CopyBeanUtils.copyBeanProperties(permission, new PermissionDto(), false);
        return new Response<>(result);
    }

    @Override
    public Response<PageInfo<PermissionDto>> pages(@RequestBody PermissionQuery query) {
        Page<Permission> page = permissionService.pagesByQuery(query);
        PageInfo<PermissionDto> result = PageToPageInfo.pageToPageInfo(page, PermissionDto.class);
        return new Response<>(result);
    }
}
