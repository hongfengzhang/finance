package com.waben.stock.interfaces.pojo.query;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Created by yuyidi on 2017/11/19.
 * @desc
 */
public class StaffQuery extends PageAndSortQuery{

    private String userName;
    private Boolean state;
    private Long role;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Long getRole() {
        return role;
    }

    public void setRole(Long role) {
        this.role = role;
    }
}
