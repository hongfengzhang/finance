package com.waben.stock.datalayer.organization.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 提现申请
 * 
 * @author luomengan
 *
 */
// @Entity
// @Table(name = "p_withdrawals_apply")
public class WithdrawalsApply {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private BigDecimal amount;
	
	
	
}
