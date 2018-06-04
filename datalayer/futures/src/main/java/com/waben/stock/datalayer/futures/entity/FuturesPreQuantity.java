package com.waben.stock.datalayer.futures.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 预购买手数
 * 
 * @author luomengan
 *
 */
@Entity
@Table(name = "f_futures_prequantity")
public class FuturesPreQuantity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 对应的合约
	 */
	@ManyToOne
	@JoinColumn(name = "contract_id")
	@JsonIgnore
	private FuturesContract contract;
	/**
	 * 手数
	 */
	private Integer quantity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FuturesContract getContract() {
		return contract;
	}

	public void setContract(FuturesContract contract) {
		this.contract = contract;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
