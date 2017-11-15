package com.waben.stock.applayer.operation.proxy;

import com.waben.stock.applayer.operation.warpper.auth.AccountCredentials;
import com.waben.stock.applayer.operation.warpper.auth.Authority;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> authorization = new ArrayList<>();
        authorization.add(new Authority("ROLE_ADMIN"));
        AccountCredentials accountCredentials = new AccountCredentials("admin", "$2a$10$pdbpGd5kC8gNYQWqtvIsGOV9A9XCOU7xq4hYIzcdBGPgixj3J.Ha.", authorization);
        return accountCredentials;
    }
}
