package com.waben.stock.datalayer.promotion.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 结构
 * 
 * @author luomengan
 *
 */
@Entity
@Table(name = "organization")
public class Organization {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 结构代码
	 */
	private String code;
	/**
	 * 结构名称
	 */
	private String name;
	/**
	 * 所属类别
	 */
	@ManyToOne
	@JoinColumn(name = "category_id")
	private OrganizationCategory category;
	/**
	 * 父级类别
	 */
	private Long parentId;
	/**
	 * 创建时间
	 */
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public OrganizationCategory getCategory() {
		return category;
	}

	public void setCategory(OrganizationCategory category) {
		this.category = category;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
