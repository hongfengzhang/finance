package com.waben.stock.applayer.promotion.controller;

import com.waben.stock.applayer.promotion.business.MenuBusiness;
import com.waben.stock.applayer.promotion.business.OrganizationBusiness;
import com.waben.stock.applayer.promotion.business.PermissionBusiness;
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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/role")
@Api(description = "角色")
public class RoleController {

    @Autowired
    private RoleBusiness roleBusiness;

    @Autowired
    private OrganizationBusiness organizationBusiness;

    @Autowired
    private MenuBusiness menuBusiness;

//    @PreAuthorize("hasRole('SAVE')")
    @RequestMapping(value = "/",method = RequestMethod.POST)
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "name", value = "角色名称", required = true),@ApiImplicitParam(paramType = "query", dataType = "String", name = "menuIds", value = "菜单id数组", required = true)})
    @ApiOperation(value = "添加角色")
    public Response<RoleVo> add(String name, String menuIds){
        RoleDto roleDto = roleBusiness.save(name,menuIds);
        RoleVo roleVo = CopyBeanUtils.copyBeanProperties(RoleVo.class,roleDto , false);
        return new Response<>(roleVo);
    }

//    @PreAuthorize("hasRole('REVISION')")
    @RequestMapping(value = "/",method = RequestMethod.PUT)
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "Long", name = "id", value = "角色id", required = true),@ApiImplicitParam(paramType = "query", dataType = "String", name = "name", value = "角色名称", required = true),@ApiImplicitParam(paramType = "query", dataType = "String", name = "menuIds", value = "菜单id数组", required = true)})
    @ApiOperation(value = "修改角色")
    public Response<RoleVo> modify(@RequestParam Long id,@RequestParam String name,@RequestParam String menuIds){
        RoleDto roleDto = roleBusiness.revision(id,name,menuIds);
        RoleVo roleVo = CopyBeanUtils.copyBeanProperties(RoleVo.class,roleDto , false);
        return new Response<>(roleVo);
    }

//    @PreAuthorize("hasRole('DELETE')")
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    @ApiImplicitParam(paramType = "path", dataType = "Long", name = "id", value = "角色id", required = true)
    @ApiOperation(value = "删除角色")
    public Response<Long> drop(@PathVariable Long id) {
        roleBusiness.remove(id);
        return new Response<>(id);
    }
//    @PreAuthorize("hasRole('BIND_MENU')")
//    @RequestMapping("/menu/{id}")
//    public Response<RoleVo> addRoleMenu(@PathVariable Long id,Long[] menuIds){
//        RoleDto roleDto = roleBusiness.saveRoleMenu(id,menuIds);
//        RoleVo roleVo = CopyBeanUtils.copyBeanProperties(RoleVo.class,roleDto , false);
//        return new Response<>(roleVo);
//    }

//    @PreAuthorize("hasRole('AUTHORIZE')")
//    @RequestMapping("/permission/{id}")
//    public Response<RoleVo> addRolePermission(@PathVariable Long id,final Long[] permissionIds){
//        RoleDto roleDto = roleBusiness.saveRolePermission(id,permissionIds);
//        RoleVo roleVo = CopyBeanUtils.copyBeanProperties(RoleVo.class,roleDto , false);
//        return new Response<>(roleVo);
//    }

    @RequestMapping(value = "/pages",method = RequestMethod.GET)
    @ApiImplicitParam(paramType = "query", dataType = "RoleQuery", name = "query", value = "角色查询对象", required = false)
    @ApiOperation(value = "角色分页")
    public Response<PageInfo<RoleVo>> pages(RoleQuery query) {
        PageInfo<RoleDto> pageInfo = roleBusiness.pages(query);
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

//    @RequestMapping(value = "/permissions",method = RequestMethod.GET)
//    @ApiOperation(value = "获取权限")
//    public Response<List<PermissionVo>> permissions() {
////        List<PermissionDto> permissions = SecurityAccount.current().getPermissions();
//        List<PermissionDto> permissions = permissionBusiness.findPermissionsByVariety();
//        List<PermissionVo> permissionVos = CopyBeanUtils.copyListBeanPropertiesToList(permissions, PermissionVo.class);
//        for(PermissionVo permissionVo : permissionVos) {
//            if(permissionVo.getPid()==0) {
//                List<PermissionVo> childPermissions = new ArrayList();
//                for(PermissionVo permission : permissionVos) {
//                    if(permission.getPid()==permissionVo.getId()) {
//                        childPermissions.add(permission);
//                        permissionVos.remove(permission);
//                    }
//                }
//                permissionVo.setChildPermissions(childPermissions);
//            }
//        }
//        return new Response<>(permissionVos);
//    }

    @RequestMapping(value = "/menus",method = RequestMethod.GET)
    @ApiOperation(value = "获取菜单")
    public Response<List<MenuVo>> menus() {
//        List<MenuDto> menuDtos = SecurityAccount.current().getMenus();
        List<MenuDto> menuDtos = menuBusiness.findMenusByVariety(4L);
        List<MenuVo> menuVos = CopyBeanUtils.copyListBeanPropertiesToList(menuDtos, MenuVo.class);
        return new Response<>(menuVos);
    }

//    @RequestMapping("/menus")
//    public Response<List<MenuVo>> menus() {
//        UserDto userDto = (UserDto) SecurityAccount.current().getSecurity();
//        Set<MenuDto> menuDtos = roleBusiness.findById(userDto.getRole()).getMenusDtos();
//        List<MenuDto> menus = new ArrayList<>();
//        menus.addAll(menuDtos);
//        List<MenuVo> menuVos = CopyBeanUtils.copyListBeanPropertiesToList(menus, MenuVo.class);
//        return new Response<>(menuVos);
//    }

//    @RequestMapping("/{id}")
//    public Response<RoleVo> fetchById(@PathVariable Long id){
//        RoleDto roleDto = roleBusiness.findById(id);
//        RoleVo roleVo = CopyBeanUtils.copyBeanProperties(RoleVo.class,roleDto , false);
//        return new Response<>(roleVo);
//    }



}
