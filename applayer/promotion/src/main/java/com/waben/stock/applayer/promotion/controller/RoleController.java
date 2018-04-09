package com.waben.stock.applayer.promotion.controller;

import com.waben.stock.applayer.promotion.business.OrganizationBusiness;
import com.waben.stock.applayer.promotion.business.RoleBusiness;
import com.waben.stock.applayer.promotion.util.SecurityAccount;
import com.waben.stock.interfaces.dto.manage.MenuDto;
import com.waben.stock.interfaces.dto.manage.PermissionDto;
import com.waben.stock.interfaces.dto.manage.RoleDto;
import com.waben.stock.interfaces.dto.organization.OrganizationDetailDto;
import com.waben.stock.interfaces.dto.organization.UserDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.RoleQuery;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.vo.manage.MenuVo;
import com.waben.stock.interfaces.vo.manage.PermissionVo;
import com.waben.stock.interfaces.vo.manage.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleBusiness roleBusiness;

    @Autowired
    private OrganizationBusiness organizationBusiness;

    @PreAuthorize("hasRole('SAVE')")
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

    @PreAuthorize("hasRole('REVISION')")
    @RequestMapping("/modify")
    @ResponseBody
    public Response<RoleVo> modify(RoleVo vo){
        UserDto userDto = (UserDto) SecurityAccount.current().getSecurity();
        vo.setOrganization(userDto.getOrg().getId());
        RoleDto requestDto = CopyBeanUtils.copyBeanProperties(RoleDto.class, vo, false);
        RoleDto roleDto = roleBusiness.revision(requestDto);
        RoleVo roleVo = CopyBeanUtils.copyBeanProperties(RoleVo.class,roleDto , false);
        return new Response<>(roleVo);
    }

    @PreAuthorize("hasRole('BIND_MENU')")
    @RequestMapping("/menu/{id}")
    @ResponseBody
    public Response<RoleVo> addRoleMenu(@PathVariable Long id,Long[] menuIds){
        RoleDto roleDto = roleBusiness.saveRoleMenu(id,menuIds);
        RoleVo roleVo = CopyBeanUtils.copyBeanProperties(RoleVo.class,roleDto , false);
        return new Response<>(roleVo);
    }

    @PreAuthorize("hasRole('AUTHORIZE')")
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
        UserDto userDto = (UserDto) SecurityAccount.current().getSecurity();
        Set<PermissionDto> permissionDtos = roleBusiness.findById(userDto.getRole()).getPermissionDtos();
        List<PermissionDto> permissions = new ArrayList<>();
        permissions.addAll(permissionDtos);
        List<PermissionVo> permissionVos = CopyBeanUtils.copyListBeanPropertiesToList(permissions, PermissionVo.class);
        return new Response<>(permissionVos);
    }

    @RequestMapping("/menus")
    @ResponseBody
    public Response<List<MenuVo>> menus() {
        UserDto userDto = (UserDto) SecurityAccount.current().getSecurity();
        Set<MenuDto> menuDtos = roleBusiness.findById(userDto.getRole()).getMenusDtos();
        List<MenuDto> menus = new ArrayList<>();
        menus.addAll(menuDtos);
        List<MenuVo> menuVos = CopyBeanUtils.copyListBeanPropertiesToList(menus, MenuVo.class);
        return new Response<>(menuVos);
    }

    @RequestMapping("/{id}")
    @ResponseBody
    public Response<RoleVo> fetchById(@PathVariable Long id){
        RoleDto roleDto = roleBusiness.findById(id);
        RoleVo roleVo = CopyBeanUtils.copyBeanProperties(RoleVo.class,roleDto , false);
        return new Response<>(roleVo);
    }

    @RequestMapping("/org/{orgId}")
    @ResponseBody
    public Response<List<RoleVo>> fetchByOrganization(@PathVariable Long orgId){
        List<RoleDto> roleDtos = roleBusiness.findByOrganization(orgId);
        List<RoleVo> roleVos = CopyBeanUtils.copyListBeanPropertiesToList(roleDtos,RoleVo.class);
        return new Response<>(roleVos);
    }


}
