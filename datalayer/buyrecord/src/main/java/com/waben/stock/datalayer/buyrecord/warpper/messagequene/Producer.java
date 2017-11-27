package com.waben.stock.datalayer.buyrecord.warpper.messagequene;

/**
 * @author Created by yuyidi on 2017/11/27.
 * @desc
 */
public interface Producer {

    void send();

    void callback();
}
