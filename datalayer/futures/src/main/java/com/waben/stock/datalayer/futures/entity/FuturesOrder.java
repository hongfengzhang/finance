package com.waben.stock.datalayer.futures.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 交易订单实体
 * 
 * @author sl
 *
 */
@Entity
@Table(name = "f_futures_order")
public class FuturesOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 对应的网关交易所ID
	 */
	private Long gatewayId;

}
