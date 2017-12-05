package com.waben.stock.interfaces.pojo.stock;

import com.waben.stock.interfaces.enums.BuyRecordState;
import com.waben.stock.interfaces.enums.EntrustState;
import com.waben.stock.interfaces.enums.EntrustType;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Created by yuyidi on 2017/12/2.
 * @desc 券商股票委托买入卖出传输对象
 */
public class SecuritiesStockEntrust implements Serializable{

    private Long buyRecordId;
    private String serialCode;
    private String tradeNo;
    private String stockName;
    private String stockCode;

    /**
     * 证券股票类型(上证|深证|创业板)
     */
    private String exponent;
    /**
     * 状态
     */
    private BuyRecordState buyRecordState;
    /**
     * 买入价格
     */
    private BigDecimal buyingPrice;
    /**
     * 买入数量
     */
    private Integer buyingNumber;
    /**
     * 成交价格
     */
    private BigDecimal dealPrice;
    /**
     * 成交数量
     */
    private Integer dealNumber;
    /**
     * 交易类型(买入或卖出)
     */
    private EntrustType entrustType;

    /**
     * 委托状态
     */
    private EntrustState entrustState;

    /**
     * 委托编号，证券账号购买股票后的交易编号
     */
    private String entrustNumber;
    /**
     * 委托时间
     */
    private String entrustTime;

    private String tradeSession;


    public Long getBuyRecordId() {
        return buyRecordId;
    }

    public void setBuyRecordId(Long buyRecordId) {
        this.buyRecordId = buyRecordId;
    }

    public String getSerialCode() {
        return serialCode;
    }

    public void setSerialCode(String serialCode) {
        this.serialCode = serialCode;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getExponent() {
        return exponent;
    }

    public void setExponent(String exponent) {
        this.exponent = exponent;
    }

    public BuyRecordState getBuyRecordState() {
        return buyRecordState;
    }

    public void setBuyRecordState(BuyRecordState buyRecordState) {
        this.buyRecordState = buyRecordState;
    }

    public BigDecimal getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(BigDecimal buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public Integer getBuyingNumber() {
        return buyingNumber;
    }

    public void setBuyingNumber(Integer buyingNumber) {
        this.buyingNumber = buyingNumber;
    }

    public BigDecimal getDealPrice() {
        return dealPrice;
    }

    public void setDealPrice(BigDecimal dealPrice) {
        this.dealPrice = dealPrice;
    }

    public Integer getDealNumber() {
        return dealNumber;
    }

    public void setDealNumber(Integer dealNumber) {
        this.dealNumber = dealNumber;
    }

    public EntrustType getEntrustType() {
        return entrustType;
    }

    public void setEntrustType(EntrustType entrustType) {
        this.entrustType = entrustType;
    }

    public EntrustState getEntrustState() {
        return entrustState;
    }

    public void setEntrustState(EntrustState entrustState) {
        this.entrustState = entrustState;
    }

    public String getEntrustNumber() {
        return entrustNumber;
    }

    public void setEntrustNumber(String entrustNumber) {
        this.entrustNumber = entrustNumber;
    }

    public String getEntrustTime() {
        return entrustTime;
    }

    public void setEntrustTime(String entrustTime) {
        this.entrustTime = entrustTime;
    }

    public String getTradeSession() {
        return tradeSession;
    }

    public void setTradeSession(String tradeSession) {
        this.tradeSession = tradeSession;
    }
}
