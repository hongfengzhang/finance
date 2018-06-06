package com.waben.stock.interfaces.dto.futures;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

/**
 * 成交统计记录
 * 
 * @author sl
 *
 */
public class TurnoverStatistyRecordDto {

	/**
	 * 成交次数
	 */
	@ApiModelProperty(value = "成交次数")
	private Integer turnoverNum;
	/**
	 * 成交手数
	 */
	@ApiModelProperty(value = "成交手数")
	private Integer turnoverHandsNum;
	/**
	 * 手续费
	 */
	@ApiModelProperty(value = "手续费")
	private BigDecimal serviceFee;
	/**
	 * 盈亏
	 */
	@ApiModelProperty(value = "盈亏")
	private BigDecimal profitAndLoss;

	public Integer getTurnoverNum() {
		return turnoverNum;
	}

	public void setTurnoverNum(Integer turnoverNum) {
		this.turnoverNum = turnoverNum;
	}

	public Integer getTurnoverHandsNum() {
		return turnoverHandsNum;
	}

	public void setTurnoverHandsNum(Integer turnoverHandsNum) {
		this.turnoverHandsNum = turnoverHandsNum;
	}

	public BigDecimal getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}

	public BigDecimal getProfitAndLoss() {
		return profitAndLoss;
	}

	public void setProfitAndLoss(BigDecimal profitAndLoss) {
		this.profitAndLoss = profitAndLoss;
	}

}
