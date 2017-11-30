package com.waben.stock.datalayer.stockcontent.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Created by yuyidi on 2017/11/22.
 * @desc 策略类型
 */
@Entity
@Table(name = "strategy_type")
public class StrategyType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private Boolean state;
    /**
     * 市值集合
     */
    @JsonManagedReference
    @JoinTable(name = "strategy_amount", joinColumns = {@JoinColumn(name = "strategy_type")}, inverseJoinColumns =
            {@JoinColumn(name = "amount_value")})
    @ManyToMany(targetEntity = AmountValue.class, fetch = FetchType.EAGER)
    private Set<AmountValue> amountValues = new HashSet<>();
    /**
     * 止损点集合
     */
    @JsonManagedReference
    @JoinTable(name = "strategy_loss", joinColumns = {@JoinColumn(name = "strategy_type")}, inverseJoinColumns =
            {@JoinColumn(name = "loss")})
    @ManyToMany(targetEntity = Loss.class, fetch = FetchType.EAGER)
    private Set<Loss> losses = new HashSet<>();
    /**
     * 止盈点
     */
    @Column
    private BigDecimal profit;
    /**
     * 递延费
     */
    @Column
    private Integer deferred;
    /**
     * 周期
     */
    @Column
    private Integer cycle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Set<AmountValue> getAmountValues() {
        return amountValues;
    }

    public void setAmountValues(Set<AmountValue> amountValues) {
        this.amountValues = amountValues;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public Set<Loss> getLosses() {
        return losses;
    }

    public void setLosses(Set<Loss> losses) {
        this.losses = losses;
    }

    public Integer getDeferred() {
        return deferred;
    }

    public void setDeferred(Integer deferred) {
        this.deferred = deferred;
    }

    public void addAmountValues(List<AmountValue> amountValues) {
        this.getAmountValues().addAll(amountValues);
    }

    public void addLosses(List<Loss> losses) {
        this.getLosses().addAll(losses);
    }

    public Integer getCycle() {
        return cycle;
    }

    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }
}
