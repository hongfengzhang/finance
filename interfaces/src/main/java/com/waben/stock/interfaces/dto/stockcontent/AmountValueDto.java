package com.waben.stock.interfaces.dto.stockcontent;

/**
 * @author Created by yuyidi on 2017/11/23.
 * @desc
 */
public class AmountValueDto implements Comparable<AmountValueDto> {

	private Long id;
	private Long value;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	@Override
	public int compareTo(AmountValueDto o) {
		return new Long(value - o.getValue()).intValue();
	}

}
