package com.waben.stock.applayer.operation.service.security;

import com.waben.stock.applayer.operation.service.investor.InvestorService;
import com.waben.stock.applayer.operation.service.manage.RoleService;
import com.waben.stock.applayer.operation.warpper.auth.AccountCredentials;
import com.waben.stock.applayer.operation.warpper.auth.RolePermissionAuthority;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.investor.InvestorDto;
import com.waben.stock.interfaces.dto.manage.RoleDto;
import com.waben.stock.interfaces.dto.manage.StaffDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author yuyidi 2017-07-13 10:16:15
 * @class com.wangbei.proxy.DaoUserDetailService
 * @description Security 用户授权访问Dao实现
 */
@Component
public class InvestorUserDetailService implements UserDetailsService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("investerFeignService")
    private InvestorService investorService;
    @Autowired
    @Qualifier("roleFeignService")
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Response<InvestorDto> response = investorService.fetchByUserName(username);
        if (response.getCode().equals("200")) {
            InvestorDto investorDto = response.getResult();
            logger.info("用户信息获取成功:{}",investorDto.getUserName());
            //绑定角色权限
            List<RolePermissionAuthority> authority = new ArrayList<>();
            Response<RoleDto> roleResponse = roleService.fetchByRoleId(investorDto.getRole());
            logger.info("获取角色信息:{}", JacksonUtil.encode(roleResponse));
            Long role;
            if (roleResponse.getCode().equals("200")) {
                RoleDto roleDto = roleResponse.getResult();
                RolePermissionAuthority rolePermissionAuthority = new RolePermissionAuthority(roleDto.getCode());
                authority.add(rolePermissionAuthority);
                role = roleDto.getId();
            }else{
                throw new ServiceException(ExceptionConstant.ROLE_NOT_FOUND_EXCEPTION);
            }
            AccountCredentials accountCredentials = new AccountCredentials(investorDto.getUserName(), investorDto
                    .getPassword(), authority);
            //获取员工权限
//            Response<List<MenuDto>> menusResponse = menuService.menusByStaff(staffDto.getId());
//            if (menusResponse.getCode() != "200") {
//                throw new ServiceException(menusResponse.getCode());
//            }
//            List<MenuDto> menus = menusResponse.getResult();
//            accountCredentials.setMenus(menus);
            accountCredentials.setRole(role);
            accountCredentials.setOperator(false);
            accountCredentials.setSecurity(investorDto);
            return accountCredentials;
        }
        throw new UsernameNotFoundException("当前用户找不到");
    }
}
