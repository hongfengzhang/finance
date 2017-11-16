package com.waben.stock.applayer.operation.service.security;

import com.waben.stock.applayer.operation.service.manage.RoleService;
import com.waben.stock.applayer.operation.service.manage.StaffService;
import com.waben.stock.applayer.operation.warpper.auth.AccountCredentials;
import com.waben.stock.applayer.operation.warpper.auth.RolePermissionAuthority;
import com.waben.stock.interfaces.dto.MenuDto;
import com.waben.stock.interfaces.dto.RoleDto;
import com.waben.stock.interfaces.dto.StaffDto;
import com.waben.stock.interfaces.pojo.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.List;

/**
 * @author Created by yuyidi on 2017/11/15.
 * @desc
 */
@Component
public class ManagerUserDetailService implements UserDetailsService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private StaffService staffService;
    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Response<StaffDto> response = staffService.fetchByUserName(username);
        if (response.getCode() != "200") {
            StaffDto staffDto = response.getResult();
            //绑定角色权限
            List<RolePermissionAuthority> authority = new ArrayList<>();
            Response<Set<RoleDto>> roleResponse = roleService.fetchAllByStaff(staffDto.getId());
            Iterator<RoleDto> it = roleResponse.getResult().iterator();
            Set<MenuDto> menus = new HashSet<>();
            while (it.hasNext()) {
                RoleDto roleDto = it.next();
                RolePermissionAuthority rolePermissionAuthority = new RolePermissionAuthority(roleDto.getCode());
//                Set<String> permissions = new HashSet<>();
//                for (PermissionDto permissionDto : roleDto.getPermissions()) {
//                    permissions.add(permissionDto.getExpression());
//                    logger.info("权限:{}",permissionDto.getExpression());
//                }
//                rolePermissionAuthority.setPermissions(permissions);
//                menus.addAll(roleDto.getMenus());
                authority.add(rolePermissionAuthority);
            }
            AccountCredentials accountCredentials = new AccountCredentials(staffDto.getUserName(), staffDto
                    .getPassword(), authority);
            return accountCredentials;
        }
        throw new UsernameNotFoundException("当前用户找不到");
    }

    private List<MenuDto> menu(MenuDto menuDto, Integer pid) {
        return null;
    }
}
