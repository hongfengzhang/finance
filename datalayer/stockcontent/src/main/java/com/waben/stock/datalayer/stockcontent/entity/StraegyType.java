package com.waben.stock.datalayer.stockcontent.entity;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Created by yuyidi on 2017/11/22.
 * @desc 策略类型
 */
@Entity
@Table(name = "strategy_type")
public class StraegyType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private Boolean state;

    @JoinTable(name = "strategy_amount", joinColumns = {@JoinColumn(name = "strategy_type")}, inverseJoinColumns =
            {@JoinColumn(name = "amount_value")})
    @ManyToMany(targetEntity = AmountValue.class, fetch = FetchType.EAGER)
    private Set<AmountValue> amountValues;

    @JoinTable(name = "strategy_profit", joinColumns = {@JoinColumn(name = "strategy_type")}, inverseJoinColumns =
            {@JoinColumn(name = "profit")})
    @ManyToMany(targetEntity = AmountValue.class, fetch = FetchType.EAGER)
    private Set<Profit> profits;
    @JoinTable(name = "strategy_loss", joinColumns = {@JoinColumn(name = "strategy_type")}, inverseJoinColumns =
            {@JoinColumn(name = "loss")})
    @ManyToMany(targetEntity = AmountValue.class, fetch = FetchType.EAGER)
    private Set<Loss> losses;

    @JoinColumn(name = "deferrd", referencedColumnName = "id")
    @ManyToOne(targetEntity = Deferred.class)
    private Deferred deferred;

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

    public Set<Profit> getProfits() {
        return profits;
    }

    public void setProfits(Set<Profit> profits) {
        this.profits = profits;
    }

    public Set<Loss> getLosses() {
        return losses;
    }

    public void setLosses(Set<Loss> losses) {
        this.losses = losses;
    }

    public Deferred getDeferred() {
        return deferred;
    }

    public void setDeferred(Deferred deferred) {
        this.deferred = deferred;
    }
}
