package com.waben.stock.interfaces.vo.manage;

import java.io.Serializable;

/**
 * @author Created by yuyidi on 2017/11/16.
 * @desc
 */
public class PermissionVo implements Serializable {

    private Long id;
    private String name;
    private Long pid;
    private String expression;

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

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}
