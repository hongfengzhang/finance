package com.waben.stock.interfaces.dto.investor;

import java.math.BigDecimal;

/**
 * @author Created by yuyidi on 2017/11/29.
 * @desc
 */
public class SecurityAccountDto {

    private Long id;
    /**
     * 股票账户
     */
    private String account;

    /**
     * 账户余额
     */
    private BigDecimal amount;
    /**
     * 可用余额
     */
    private BigDecimal availability;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAvailability() {
        return availability;
    }

    public void setAvailability(BigDecimal availability) {
        this.availability = availability;
    }
}
