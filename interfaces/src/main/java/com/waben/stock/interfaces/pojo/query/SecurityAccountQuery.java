package com.waben.stock.interfaces.pojo.query;

/**
 * @author Created by yuyidi on 2017/11/30.
 * @desc
 */
public class SecurityAccountQuery extends PageAndSortQuery {
    private String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
