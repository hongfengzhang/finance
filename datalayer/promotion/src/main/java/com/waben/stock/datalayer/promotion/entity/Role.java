package com.waben.stock.datalayer.promotion.entity;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.waben.stock.datalayer.promotion.entity.enumconverter.PromotionRoleTypeTypeConverter;
import com.waben.stock.interfaces.enums.PromotionRoleType;

/**
 * 角色
 * 
 * @author luomengan
 *
 */
@Entity
@Table(name = "p_role")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 角色名称
	 */
	private String name;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 角色类型
	 */
	@Convert(converter = PromotionRoleTypeTypeConverter.class)
	private PromotionRoleType type;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public PromotionRoleType getType() {
		return type;
	}

	public void setType(PromotionRoleType type) {
		this.type = type;
	}

}
