package com.waben.stock.applayer.operation.warpper.auth;

import com.waben.stock.interfaces.dto.manage.MenuDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
* @author yuyidi 2017-07-08 20:23:36
* @class com.wangbei.awre.auth.AccountCredentials
* @description 账户凭证 若后面需要实现账户锁定，实现UserDetails接口
*/
public class AccountCredentials extends User implements Serializable {

    private List<MenuDto> menus;
    private Long staff;

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

    public List<MenuDto> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuDto> menus) {
        this.menus = menus;
    }

    public Long getStaff() {
        return staff;
    }

    public void setStaff(Long staff) {
        this.staff = staff;
    }
}
