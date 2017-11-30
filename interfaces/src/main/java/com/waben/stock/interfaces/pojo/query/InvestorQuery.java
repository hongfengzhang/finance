package com.waben.stock.interfaces.pojo.query;

/**
 * @author Created by yuyidi on 2017/11/30.
 * @desc
 */
public class InvestorQuery extends PageAndSortQuery {
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
