package com.waben.stock.applayer.operation.service.security;

import com.waben.stock.applayer.operation.warpper.auth.AccountCredentials;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuyidi 2017-07-13 10:16:15
 * @class com.wangbei.proxy.DaoUserDetailService
 * @description Security 用户授权访问Dao实现
 */
@Component
public class InvestorUserDetailService implements UserDetailsService {

//    @Autowired
//    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
//        User user = userService.getUserByPhone(phone);
//        if (user != null) {
//            //根据用户获取权限
//            List<GrantedAuthority> authorization = new ArrayList<>();
//            authorization.add(new RoleAuthority("ROLE_USER"));
//            AccountCredentials accountCredentials = new AccountCredentials(user.getPhone(), user.getPassword(),
// authorization);
//            return accountCredentials;
//        }
        //        throw new UsernameNotFoundException("当前用户找不到");
        List<GrantedAuthority> authorization = new ArrayList<>();
//        authorization.add(new RoleAuthority("ROLE_USER"));
        AccountCredentials accountCredentials = new AccountCredentials("user",
                "$2a$10$KspDy5bHoUuuQ8setXjf9eqq4o/D567LUl77uwTQlD8N5G6ZGTgqu", authorization);
        return accountCredentials;
    }
}
