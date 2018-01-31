package com.waben.stock.datalayer.promotion.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 结构类别
 * 
 * @author luomengan
 *
 */
@Entity
@Table(name = "organization_category")
public class OrganizationCategory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
}
