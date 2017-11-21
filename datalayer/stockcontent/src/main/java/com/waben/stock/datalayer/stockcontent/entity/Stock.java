package com.waben.stock.datalayer.stockcontent.entity;

import java.util.Date;

import javax.persistence.*;

import com.waben.stock.interfaces.dto.stockcontent.StockDto;

import net.sf.cglib.beans.BeanCopier;

/**
 * 股票
 * 
 * @author luomengan
 *
 */
@Entity
@Table(name = "stock")
public class Stock {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 股票名称
	 */
	@Column
	private String name;
	/**
	 * 股票代码
	 */
	@Column(unique = true,length = 10)
	private String code;
	/**
	 * 股票状态(可买可卖状态 非开始闭市状态)
	 */
	@Column
	private Boolean status;
	/**
	 * 所属交易指数
	 */
	@JoinColumn(name = "exponent",referencedColumnName = "exponent_code")
	@ManyToOne(targetEntity = StockExponent.class)
	private StockExponent exponent;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public StockExponent getExponent() {
		return exponent;
	}

	public void setExponent(StockExponent exponent) {
		this.exponent = exponent;
	}
}
