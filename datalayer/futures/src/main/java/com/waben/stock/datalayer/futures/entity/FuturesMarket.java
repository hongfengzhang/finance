package com.waben.stock.datalayer.futures.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 期货市场实体
 * 
 * @author sl
 *
 */
@Entity
@Table(name = "f_futures_market")
public class FuturesMarket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 市场类别
	 */
	private String marketType;

	/**
	 * 市场名称
	 */
	private String name;

	/**
	 * 境外标记
	 * 
	 * <p> 1  外盘 </p>
	 * <p> 2  内盘 </p>
	 */
	private Boolean sign;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMarketType() {
		return marketType;
	}

	public void setMarketType(String marketType) {
		this.marketType = marketType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getSign() {
		return sign;
	}

	public void setSign(Boolean sign) {
		this.sign = sign;
	}

}
