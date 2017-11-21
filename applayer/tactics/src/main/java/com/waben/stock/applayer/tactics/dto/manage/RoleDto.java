package com.waben.stock.applayer.tactics.dto.manage;

import java.io.Serializable;
import java.util.Set;

/**
 * @author Created by yuyidi on 2017/11/16.
 * @desc
 */
public class RoleDto implements Serializable{

    private Long id;
    private String name;
    private String code;
    private String describtion;
    private Set<PermissionDto> permissionDtos;
    private Set<MenuDto> menusDtos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescribtion() {
        return describtion;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
    }

    public Set<PermissionDto> getPermissionDtos() {
        return permissionDtos;
    }

    public void setPermissionDtos(Set<PermissionDto> permissionDtos) {
        this.permissionDtos = permissionDtos;
    }

    public Set<MenuDto> getMenusDtos() {
        return menusDtos;
    }

    public void setMenusDtos(Set<MenuDto> menusDtos) {
        this.menusDtos = menusDtos;
    }
}
