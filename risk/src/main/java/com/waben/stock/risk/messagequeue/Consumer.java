package com.waben.stock.risk.messagequeue;

/**
 * @author Created by yuyidi on 2017/11/27.
 * @desc
 */
public interface Consumer {

    void receiveDirect(String message);

    void receiveTopic(String message);

}
