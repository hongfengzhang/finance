package com.waben.stock.datalayer.message.entity;

import com.waben.stock.datalayer.message.entity.converter.MessageTypeConverter;
import com.waben.stock.interfaces.enums.MessageType;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * @author Created by yuyidi on 2018/1/3.
 * @desc
 */
@Entity
@Table(name = "messaging")
public class Messaging {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 30, nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    @Column(name = "type")
    @Convert(converter = MessageTypeConverter.class)
    private MessageType type;
    @OneToMany(mappedBy = "message")
    private Set<MessageReceipt> receipts;
    @Column(name = "create_time")
    private Date createTime;

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

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public Set<MessageReceipt> getReceipts() {
        return receipts;
    }

    public void setReceipts(Set<MessageReceipt> receipts) {
        this.receipts = receipts;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
