package com.waben.stock.interfaces.dto.organization;

import java.math.BigDecimal;
import java.util.Date;

import com.waben.stock.interfaces.enums.CapitalFlowType;

public class FuturesFowDto {

	/**
	 * 订单ID
	 */
	private Long id;

	/**
	 * 客户姓名
	 */
	private String customerName;

	/**
	 * 交易账号
	 */
	private String tradingNumber;

	/**
	 * 交易编号
	 */
	private String flowNo;

	/**
	 * 交易时间
	 */
	private Date occurrenceTime;

	/**
	 * 业务类型
	 */
	private CapitalFlowType type;

	/**
	 * 交易金额
	 */
	private BigDecimal amount;

	/**
	 * 当前可用余额
	 * <p>
	 * 产生该流水之后的可用余额
	 * </p>
	 */
	private BigDecimal availableBalance;

	/**
	 * 股票代码
	 */
	private String symbol;

	/**
	 * 标的股票
	 */
	private String contractName;

	/**
	 * 所属代理商代码
	 */
	private String agentCode;

	/**
	 * 所属代理商代码名称
	 */
	private String agentCodeName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getTradingNumber() {
		return tradingNumber;
	}

	public void setTradingNumber(String tradingNumber) {
		this.tradingNumber = tradingNumber;
	}

	public String getFlowNo() {
		return flowNo;
	}

	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}

	public Date getOccurrenceTime() {
		return occurrenceTime;
	}

	public void setOccurrenceTime(Date occurrenceTime) {
		this.occurrenceTime = occurrenceTime;
	}

	public CapitalFlowType getType() {
		return type;
	}

	public void setType(CapitalFlowType type) {
		this.type = type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(BigDecimal availableBalance) {
		this.availableBalance = availableBalance;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public String getAgentCodeName() {
		return agentCodeName;
	}

	public void setAgentCodeName(String agentCodeName) {
		this.agentCodeName = agentCodeName;
	}
}
