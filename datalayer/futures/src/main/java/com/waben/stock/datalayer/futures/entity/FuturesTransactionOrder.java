package com.waben.stock.datalayer.futures.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.waben.stock.datalayer.futures.generators.OrderSequenceGenerator;

/**
 * 交易订单实体
 * 
 * @author sl
 *
 */
@Entity
@Table(name = "f_futures_transaction_order")
public class FuturesTransactionOrder {

	@Id
	@Column(length = 50)
	@GeneratedValue(generator = "orderSequenceGenerator")
	@GenericGenerator(name = "orderSequenceGenerator", 
			strategy = OrderSequenceGenerator.TYPE,
			parameters = @Parameter(name = "sequence_name", value = "order"))
	private Long orderId;

}
