package com.waben.stock.interfaces.pojo.stock.stockjy;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Created by yuyidi on 2017/11/30.
 * @desc
 */
public class StockLoginInfo {

    String clientId;
    String clientName;
    String fundAccount;
    String tradeSession;

    public String getClientId() {
        return clientId;
    }

    @JsonProperty("client_id")
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    @JsonProperty("client_name")
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getFundAccount() {
        return fundAccount;
    }

    @JsonProperty("fund_account")
    public void setFundAccount(String fundAccount) {
        this.fundAccount = fundAccount;
    }

    public String getTradeSession() {
        return tradeSession;
    }

    @JsonProperty("trade_session")
    public void setTradeSession(String tradeSession) {
        this.tradeSession = tradeSession;
    }
}
