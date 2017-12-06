package com.waben.stock.interfaces.pojo.query;

/**
 * @author Created by yuyidi on 2017/12/6.
 * @desc
 */
public class StrategyTypeQuery extends PageAndSortQuery {
    Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
