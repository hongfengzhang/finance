package com.waben.stock.datalayer.manage.entity;

import org.springframework.boot.actuate.endpoint.jmx.DataEndpointMBean;

import javax.persistence.*;
import java.util.Date;

/***
* @author yuyidi 2017-11-13 22:12:35
* @class com.waben.stock.datalayer.manage.entity.Banner
* @description 轮播
*/
@Entity
@Table(name = "banner")
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String link;
    @Column
    private String describtion;
    @Column
    private Integer sort;
    @Column(length = 1)
    private Boolean state;
    @Column(name = "create_time")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescribtion() {
        return describtion;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
