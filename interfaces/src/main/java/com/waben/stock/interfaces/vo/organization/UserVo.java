package com.waben.stock.interfaces.vo.organization;

import com.waben.stock.interfaces.dto.organization.OrganizationDto;

import java.util.Date;

public class UserVo {
    private Long id;
    /**
     * 登陆用户名
     */
    private String username;
    /**
     * 登陆密码
     */
    private String password;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 所属机构
     */
    private OrganizationVo org;

    private Long role;
    private String roleName;
    private String code;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public OrganizationVo getOrg() {
        return org;
    }

    public void setOrg(OrganizationVo org) {
        this.org = org;
    }

    public void setRole(Long role) {
        this.role = role;
    }

    public Long getRole() {
        return role;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
