package com.waben.stock.applayer.tactics.dto.futures;

import com.waben.stock.interfaces.commonapi.retrivefutures.bean.FuturesContractMarket;

import io.swagger.annotations.ApiModelProperty;

/**
 * 合约与行情Dto
 * 
 * @author Administrator
 *
 */
public class FuturesContractMarketDto extends FuturesContractMarket {

	/**
	 * 期货合约状态
	 * 
	 * <p>
	 * 1 交易中
	 * </p>
	 * <p>
	 * 2 休市中
	 * </p>
	 * <p>
	 * 3 异常
	 * </p>
	 */
	@ApiModelProperty(value = "期货合约状态")
	private Integer contractState;

	/**
	 * 合约名称
	 */
	@ApiModelProperty(value = "期货合约名称")
	private String contractName;

	/**
	 * 本时段持仓时间节点
	 */
	@ApiModelProperty(value = "本时段持仓时间节点")
	private String currentHoldingTime;

	/**
	 * 下一个交易时间
	 */
	@ApiModelProperty(value = "下一个交易时间")
	private String nextTradingTime;

	public Integer getContractState() {
		return contractState;
	}

	public void setContractState(Integer contractState) {
		this.contractState = contractState;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getCurrentHoldingTime() {
		return currentHoldingTime;
	}

	public void setCurrentHoldingTime(String currentHoldingTime) {
		this.currentHoldingTime = currentHoldingTime;
	}

	public String getNextTradingTime() {
		return nextTradingTime;
	}

	public void setNextTradingTime(String nextTradingTime) {
		this.nextTradingTime = nextTradingTime;
	}

}
