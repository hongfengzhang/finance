package com.waben.stock.datalayer.stockcontent.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Created by yuyidi on 2017/11/22.
 * @desc 止盈
 */
@Entity
@Table(name = "profit")
public class Profit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal point;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPoint() {
        return point;
    }

    public void setPoint(BigDecimal point) {
        this.point = point;
    }
}
