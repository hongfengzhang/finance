package com.waben.stock.applayer.promotion.business.security;

import com.waben.stock.applayer.promotion.business.MenuBusiness;
import com.waben.stock.applayer.promotion.business.PermissionBusiness;
import com.waben.stock.applayer.promotion.business.RoleBusiness;
import com.waben.stock.applayer.promotion.business.UserBusiness;
import com.waben.stock.applayer.promotion.reference.manage.MenuReference;
import com.waben.stock.applayer.promotion.warpper.auth.AccountCredentials;
import com.waben.stock.applayer.promotion.warpper.auth.PermissionAuthority;
import com.waben.stock.interfaces.dto.manage.MenuDto;
import com.waben.stock.interfaces.dto.manage.PermissionDto;
import com.waben.stock.interfaces.dto.manage.RoleDto;
import com.waben.stock.interfaces.dto.organization.UserDto;
import com.waben.stock.interfaces.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by yuyidi on 2017/11/15.
 * @desc
 */
@Component
public class UserDetailService implements UserDetailsService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserBusiness userBusiness;
//    @Autowired
//    private RoleBusiness roleBusiness;
    @Autowired
    private MenuBusiness menuBusiness;
    @Autowired
    private PermissionBusiness permissionBusiness;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = userBusiness.fetchByUserName(username);
        logger.info("用户信息获取成功:{}", userDto.getUsername());
        //绑定角色权限
        List<PermissionAuthority> authority = new ArrayList<>();
//        RoleDto roleDto = roleBusiness.findById(userDto.getRole());
        List<PermissionDto> permissionDtos = permissionBusiness.fetchPermissionsByRole(userDto.getRole());
        for (PermissionDto permissionDto : permissionDtos) {
            PermissionAuthority permissionAuthority = new PermissionAuthority("ROLE_"+permissionDto.getExpression());
            logger.info("添加权限:{},{}", permissionDto.getName(), permissionDto.getExpression());
            authority.add(permissionAuthority);
        }
        AccountCredentials accountCredentials = new AccountCredentials(userDto.getUsername(), userDto
                .getPassword(), authority);
        List<MenuDto> menus = menuBusiness.menus(userDto.getRole());
        logger.info("菜单项:{}", JacksonUtil.encode(menus));
        accountCredentials.setMenus(menus);
        accountCredentials.setSecurity(userDto);
        accountCredentials.setPermissions(permissionDtos);
        return accountCredentials;
    }

}
