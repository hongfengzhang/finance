package com.waben.stock.datalayer.promotion.entity;

import java.util.Date;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.waben.stock.datalayer.promotion.entity.enumconverter.OrganizationStateConverter;
import com.waben.stock.interfaces.enums.OrganizationState;

/**
 * 机构
 * 
 * @author luomengan
 *
 */
@Entity
@Table(name = "p_organization")
public class Organization {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 机构代码
	 */
	private String code;
	/**
	 * 机构名称
	 */
	private String name;
	/**
	 * 层级
	 */
	private Integer level;
	/**
	 * 机构状态
	 */
	@Convert(converter = OrganizationStateConverter.class)
	private OrganizationState state;
	/**
	 * 父级机构
	 */
	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Organization parent;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 所属类别ID
	 */
	@Transient
	private Long categoryId;
	/**
	 * 所属类别名称
	 */
	@Transient
	private String categoryName;
	/**
	 * 父级机构ID
	 */
	@Transient
	private Long parentId;
	/**
	 * 父级机构代码
	 */
	@Transient
	private String parentCode;

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

	public Organization getParent() {
		return parent;
	}

	public void setParent(Organization parent) {
		this.parent = parent;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public OrganizationState getState() {
		return state;
	}

	public void setState(OrganizationState state) {
		this.state = state;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getParentId() {
		if (parent != null) {
			return parent.getId();
		}
		return null;
	}

	public String getParentCode() {
		if (parent != null) {
			return parent.getCode();
		}
		return null;
	}

}
