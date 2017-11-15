package com.waben.stock.applayer.operation.proxy;

import com.waben.stock.applayer.operation.service.StaffService;
import com.waben.stock.applayer.operation.warpper.auth.AccountCredentials;
import com.waben.stock.applayer.operation.warpper.auth.Authority;
import com.waben.stock.interfaces.dto.StaffDto;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.security.core.GrantedAuthority;
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
public class ManagerUserDetailService implements UserDetailsService {

    @Autowired
    private StaffService staffService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Response<StaffDto> response = staffService.fetchByUserName(username);
        if (response.getCode() != "200") {
            StaffDto staffDto = response.getResult();
            //根据用户获取权限
            List<GrantedAuthority> authorization = new ArrayList<>();
            authorization.add(new Authority("ROLE_ADMIN"));
            AccountCredentials accountCredentials = new AccountCredentials(staffDto.getUserName(), staffDto.getPassword(), authorization);
            return accountCredentials;
        }
        throw new UsernameNotFoundException("当前用户找不到");
    }
}
