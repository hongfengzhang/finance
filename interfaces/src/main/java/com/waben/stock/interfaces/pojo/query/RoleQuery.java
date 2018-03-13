package com.waben.stock.interfaces.pojo.query;

/**
 * @author Created by yuyidi on 2017/12/11.
 * @desc
 */
public class RoleQuery extends PageAndSortQuery {
    private String name;
    private String code;
    private Long id;
    private String description;
    private Long organization;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getOrganization() {
        return organization;
    }

    public void setOrganization(Long organization) {
        this.organization = organization;
    }
}
