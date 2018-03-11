package com.waben.stock.interfaces.vo.manage;

import java.io.Serializable;
import java.util.Set;

/**
 * @author Created by yuyidi on 2017/11/16.
 * @desc
 */
public class RoleVo implements Serializable{

    private Long id;
    private String name;
    private String code;
    private String description;
    private Set<PermissionVo> permissionVos;
    private Set<MenuVo> menusVos;
    private Long organization;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Set<PermissionVo> getPermissionVos() {
        return permissionVos;
    }

    public void setPermissionVos(Set<PermissionVo> permissionVos) {
        this.permissionVos = permissionVos;
    }

    public Set<MenuVo> getMenusVos() {
        return menusVos;
    }

    public void setMenusVos(Set<MenuVo> menusVos) {
        this.menusVos = menusVos;
    }

    public void setMenusDtos(Set<MenuVo> menusDtos) {
        this.menusVos = menusDtos;
    }
    public void setPermissionDtos(Set<PermissionVo> permissionDtos) {
        this.permissionVos = permissionDtos;
    }

    public Long getOrganization() {
        return organization;
    }

    public void setOrganization(Long organization) {
        this.organization = organization;
    }
}
