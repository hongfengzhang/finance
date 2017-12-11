package com.waben.stock.interfaces.pojo.query;

public class StrategyHoldingQuery extends PageAndSortQuery{
    private Long publisherId;

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }
}
