package com.waben.stock.applayer.promotion.controller;

import com.waben.stock.applayer.promotion.business.RoleBusiness;
import com.waben.stock.interfaces.dto.manage.RoleDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.vo.manage.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleBusiness roleBusiness;

    @RequestMapping("/save")
    @ResponseBody
    public Response<RoleVo> add(RoleVo vo){
        RoleDto requestDto = CopyBeanUtils.copyBeanProperties(RoleDto.class, vo, false);
        RoleDto roleDto = roleBusiness.save(requestDto);
        RoleVo roleVo = CopyBeanUtils.copyBeanProperties(RoleVo.class,roleDto , false);
        return new Response<>(roleVo);
    }

    @RequestMapping("/menu/{id}")
    @ResponseBody
    public Response<RoleVo> addRoleMenu(@PathVariable Long id,Long[] menuIds){
        RoleDto roleDto = roleBusiness.saveRoleMenu(id,menuIds);
        RoleVo roleVo = CopyBeanUtils.copyBeanProperties(RoleVo.class,roleDto , false);
        return new Response<>(roleVo);
    }

    @RequestMapping("/permission/{id}")
    @ResponseBody
    public Response<RoleVo> addRolePermission(@PathVariable Long id,Long[] permissionIds){
        RoleDto roleDto = roleBusiness.saveRolePermission(id,permissionIds);
        RoleVo roleVo = CopyBeanUtils.copyBeanProperties(RoleVo.class,roleDto , false);
        return new Response<>(roleVo);
    }
}
