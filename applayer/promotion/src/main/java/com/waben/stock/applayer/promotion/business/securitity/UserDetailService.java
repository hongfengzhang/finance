package com.waben.stock.applayer.promotion.business.securitity;

import com.waben.stock.applayer.promotion.business.UserBusiness;
import com.waben.stock.applayer.promotion.warpper.auth.AccountCredentials;
import com.waben.stock.applayer.promotion.warpper.auth.RolePermissionAuthority;
import com.waben.stock.interfaces.dto.organization.OrganizationDto;
import com.waben.stock.interfaces.dto.organization.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
//    private RoleService roleService;
//    @Autowired
//    private MenuService menuService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Response<UserDto> response = userService.fetchByUserName(username);
//        UserDto userDto = response.getResult();
        UserDto userDto = userBusiness.fetchByUserName(username);
//        UserDto userDto = new UserDto();
//        OrganizationDto organizationDto = new OrganizationDto();
//        organizationDto.setId(1L);
//        userDto.setOrg(organizationDto);
//        userDto.setUsername("youguwang");
//        userDto.setPassword("$2a$10$dyn5mH6mS4cR4mU8GmbTru0vXuGDhu/D8A2fbWypqPd1xvWVMdrb.");
        logger.info("用户信息获取成功:{}", userDto.getUsername());
        //绑定角色权限
        List<RolePermissionAuthority> authority = new ArrayList<>();
//        RoleDto roleDto = response.getRoleDto();
//        RolePermissionAuthority rolePermissionAuthority = new RolePermissionAuthority(roleDto.getCode());
//        authority.add(rolePermissionAuthority);
        AccountCredentials accountCredentials = new AccountCredentials(userDto.getUsername(), userDto
                .getPassword(), authority);
        //获取员工权限
//            Response<List<MenuDto>> menusResponse = menuService.menusByStaff(staffDto.getId());
//            if (menusResponse.getCode() != "200") {
//                throw new ServiceException(menusResponse.getCode());
//            }
//            List<MenuDto> menus = menusResponse.getResult();
//            accountCredentials.setMenus(menus);
//        accountCredentials.setRole(roleDto.getId());
        accountCredentials.setSecurity(userDto);
        return accountCredentials;
    }

}
