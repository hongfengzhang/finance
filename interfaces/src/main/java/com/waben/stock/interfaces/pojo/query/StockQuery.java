package com.waben.stock.interfaces.pojo.query;

/**
 * @author Created by yuyidi on 2017/11/22.
 * @desc
 */
public class StockQuery extends PageAndSortQuery {

    private String name;
    private String code;
    private String keyword;

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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
