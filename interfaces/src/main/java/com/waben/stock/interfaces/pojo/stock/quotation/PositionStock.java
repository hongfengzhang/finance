package com.waben.stock.interfaces.pojo.stock.quotation;

import java.io.Serializable;
import java.math.BigDecimal;

public class PositionStock implements Serializable{
    private String tradeNo;
    private BigDecimal profitPoint;
    private BigDecimal lossPoint;
    private BigDecimal buyingPrice;
    private String stockCode;
    private String stockName;
    private BigDecimal profitPosition;
    private BigDecimal lossPosition;
    public BigDecimal getProfitPosition() {
        return profitPosition;
    }

    public void setProfitPosition(BigDecimal profitPosition) {
        this.profitPosition = profitPosition;
    }

    public BigDecimal getLossPosition() {
        return lossPosition;
    }

    public void setLossPosition(BigDecimal lossPosition) {
        this.lossPosition = lossPosition;
    }
    public String getTradeNo() {
        return tradeNo;
    }

    public BigDecimal getProfitPoint() {
        return profitPoint;
    }

    public BigDecimal getBuyingPrice() {
        return buyingPrice;
    }

    public String getStockCode() {
        return stockCode;
    }

    public BigDecimal getLossPoint() {
        return lossPoint;
    }

    public void setLossPoint(BigDecimal lossPoint) {
        this.lossPoint = lossPoint;
    }

    public String getStockName() {
        return stockName;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public void setProfitPoint(BigDecimal profitPoint) {
        this.profitPoint = profitPoint;
    }

    public void setBuyingPrice(BigDecimal buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }
    @Override
    public String toString() {
        return "RiskBuyInStock{" +
                "tradeNo='" + tradeNo + '\'' +
                ", profitPoint=" + profitPoint +
                ", lossPoint=" + lossPoint +
                ", buyingPrice=" + buyingPrice +
                ", stockCode='" + stockCode + '\'' +
                ", stockName='" + stockName + '\'' +
                '}';
    }
}
