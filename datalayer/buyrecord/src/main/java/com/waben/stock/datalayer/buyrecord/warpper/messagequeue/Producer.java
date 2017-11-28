package com.waben.stock.datalayer.buyrecord.warpper.messagequeue;

/**
 * @author Created by yuyidi on 2017/11/27.
 * @desc
 */
public interface Producer {

    void direct(String routingKey,String message);

    void topic(String exchange, String routingKey, String message);

    void fanout(String exchange, String message);

}
