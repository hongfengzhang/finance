package com.waben.stock.applayer.promotion.controller;

import com.waben.stock.applayer.promotion.business.OrganizationBusiness;
import com.waben.stock.applayer.promotion.business.PermissionBusiness;
import com.waben.stock.applayer.promotion.business.RoleBusiness;
import com.waben.stock.applayer.promotion.util.SecurityAccount;
import com.waben.stock.interfaces.dto.manage.PermissionDto;
import com.waben.stock.interfaces.dto.manage.RoleDto;
import com.waben.stock.interfaces.dto.organization.OrganizationDetailDto;
import com.waben.stock.interfaces.dto.organization.UserDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.RoleQuery;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.vo.manage.PermissionVo;
import com.waben.stock.interfaces.vo.manage.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleBusiness roleBusiness;

    @Autowired
    private OrganizationBusiness organizationBusiness;

    @Autowired
    private PermissionBusiness permissionBusiness;

    @RequestMapping("/save")
    @ResponseBody
    public Response<RoleVo> add(RoleVo vo){
        UserDto userDto = (UserDto) SecurityAccount.current().getSecurity();
        vo.setOrganization(userDto.getOrg().getId());
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
    public Response<RoleVo> addRolePermission(@PathVariable Long id,final Long[] permissionIds){
        RoleDto roleDto = roleBusiness.saveRolePermission(id,permissionIds);
        RoleVo roleVo = CopyBeanUtils.copyBeanProperties(RoleVo.class,roleDto , false);
        return new Response<>(roleVo);
    }

    @RequestMapping("/pages")
    @ResponseBody
    public Response<PageInfo<RoleVo>> pages(@RequestBody RoleQuery roleQuery) {
        UserDto userDto = (UserDto) SecurityAccount.current().getSecurity();
        roleQuery.setOrganization(userDto.getOrg().getId());
        PageInfo<RoleDto> pageInfo = roleBusiness.pages(roleQuery);
        List<RoleVo> roleVoContent = CopyBeanUtils.copyListBeanPropertiesToList(pageInfo.getContent(), RoleVo.class);
        PageInfo<RoleVo> response = new PageInfo<>(roleVoContent, pageInfo.getTotalPages(), pageInfo.getLast(), pageInfo.getTotalElements(), pageInfo.getSize(), pageInfo.getNumber(), pageInfo.getFrist());
        for (RoleVo role : response.getContent()) {
            if(role.getOrganization()!=null) {
                OrganizationDetailDto detail = organizationBusiness.detail(role.getOrganization());
                role.setOrganizationName(detail.getName());
            }
        }
        return new Response<>(response);
    }

    @RequestMapping("/permissions")
    @ResponseBody
    public Response<List<PermissionVo>> permissions() {
        List<PermissionDto> permissionDtos = permissionBusiness.fetchPermissions();
        List<PermissionVo> permissionVos = CopyBeanUtils.copyListBeanPropertiesToList(permissionDtos, PermissionVo.class);
        return new Response<>(permissionVos);
    }

    @RequestMapping("/{id}")
    @ResponseBody
    public Response<RoleVo> fetchById(@PathVariable Long id){
        RoleDto roleDto = roleBusiness.findById(id);
        RoleVo roleVo = CopyBeanUtils.copyBeanProperties(RoleVo.class,roleDto , false);
        return new Response<>(roleVo);
    }
}
