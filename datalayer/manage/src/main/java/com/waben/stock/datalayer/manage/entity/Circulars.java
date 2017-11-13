package com.waben.stock.datalayer.manage.entity;

import javax.persistence.*;
import java.util.Date;

/***
* @author yuyidi 2017-11-13 22:12:52
* @class com.waben.stock.datalayer.manage.entity.Circulars
* @description 通告
*/
@Entity
@Table(name = "circular")
public class Circulars {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 22)
    private String title;
    @Column
    private String content;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "expire_time")
    private Date expireTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}
