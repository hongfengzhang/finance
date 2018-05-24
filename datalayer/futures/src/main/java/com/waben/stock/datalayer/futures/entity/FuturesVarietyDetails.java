package com.waben.stock.datalayer.futures.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 期货品种详情实体
 * 
 * @author sl
 *
 */
@Entity
@Table(name = "f_variety_details")
public class FuturesVarietyDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
}
