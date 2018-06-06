package com.waben.stock.interfaces.dto.futures;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FuturesPreQuantityDto implements Comparable<FuturesPreQuantityDto> {

	private Long id;
	/**
	 * 手数
	 */
	@ApiModelProperty(value = "手数")
	private Integer quantity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Override
	public int compareTo(FuturesPreQuantityDto o) {
		return quantity.compareTo(o.getQuantity());
	}

}
