package com.waben.stock.applayer.operation.warpper.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Collection;

/**
* @author yuyidi 2017-07-08 20:23:36
* @class com.wangbei.awre.auth.AccountCredentials
* @description 账户凭证 若后面需要实现账户锁定，实现UserDetails接口
*/
public class AccountCredentials extends User implements Serializable {

    public AccountCredentials(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

}
