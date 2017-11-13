package com.waben.stock.datalayer.investors.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/***
* @author yuyidi 2017-11-13 23:17:41
* @class com.waben.stock.datalayer.investors.entity.StockAccount
* @description 股票账户
*/
@Entity
@Table(name = "stock_account")
public class StockAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "account_number")
    private String accountNumber;
    @Column
    private BigDecimal amount;
    @Column
    private BigDecimal freezing;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getFreezing() {
        return freezing;
    }

    public void setFreezing(BigDecimal freezing) {
        this.freezing = freezing;
    }
}
