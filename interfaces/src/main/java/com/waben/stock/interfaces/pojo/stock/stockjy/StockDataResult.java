package com.waben.stock.interfaces.pojo.stock.stockjy;

import java.util.List;

/**
 * @author Created by yuyidi on 2017/11/30.
 * @desc
 */
public class StockDataResult {
    private List<StockLoginInfo> data;

    public List<StockLoginInfo> getData() {
        return data;
    }

    public void setData(List<StockLoginInfo> data) {
        this.data = data;
    }
}
