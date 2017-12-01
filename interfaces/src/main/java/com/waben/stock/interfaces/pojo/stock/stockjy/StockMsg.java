package com.waben.stock.interfaces.pojo.stock.stockjy;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Created by yuyidi on 2017/11/30.
 * @desc
 */
public class StockMsg {

    private String errorInfo;

    private String errorNo;

    public String getErrorInfo() {
        return errorInfo;
    }

    @JsonProperty("error_info")
    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public String getErrorNo() {
        return errorNo;
    }

    @JsonProperty("error_no")
    public void setErrorNo(String errorNo) {
        this.errorNo = errorNo;
    }
}
