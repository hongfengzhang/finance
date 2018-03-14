package com.waben.stock.applayer.promotion.warpper.auth;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Created by yuyidi on 2017/11/16.
 * @desc
 */
public class PermissionAuthority implements GrantedAuthority {

    private final String permission;



    public PermissionAuthority(String permission) {
        this.permission = permission;
    }

    public String getAuthority() {
        return this.permission;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else {
            return obj instanceof PermissionAuthority ? this.permission.equals(((PermissionAuthority)obj).permission) : false;
        }
    }

    public int hashCode() {
        return this.permission.hashCode();
    }

    public String toString() {
        return this.permission;
    }


}
