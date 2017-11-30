package com.waben.stock.datalayer.manage.controller;

import com.waben.stock.datalayer.manage.entity.Role;
import com.waben.stock.datalayer.manage.service.RoleService;
import com.waben.stock.interfaces.dto.manage.RoleDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.service.manage.RoleInterface;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public Response<RoleDto> fetchByRoleId(Long id) {
        Role role = roleService.findById(id);
        RoleDto result = CopyBeanUtils.copyBeanProperties(role, new RoleDto(), false);
        return new Response<>(result);
    }

}
