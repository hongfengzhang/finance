package com.waben.stock.interfaces.dto.stockcontent;

import java.math.BigDecimal;

/**
 * @author Created by yuyidi on 2017/11/23.
 * @desc
 */
public class LossDto implements Comparable<LossDto> {

	private Long id;
	private BigDecimal point;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getPoint() {
		return point;
	}

	public void setPoint(BigDecimal point) {
		this.point = point;
	}

	@Override
	public int compareTo(LossDto o) {
		return o.getPoint().compareTo(point);
	}

}
