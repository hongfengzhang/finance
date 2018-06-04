package com.waben.stock.interfaces.dto.futures;

import java.math.BigDecimal;

public class FuturesPreQuantityDto implements Comparable<FuturesPreQuantityDto> {

	private Long id;
	/**
	 * 手数
	 */
	private BigDecimal quantity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	@Override
	public int compareTo(FuturesPreQuantityDto o) {
		return quantity.compareTo(o.getQuantity());
	}

}
