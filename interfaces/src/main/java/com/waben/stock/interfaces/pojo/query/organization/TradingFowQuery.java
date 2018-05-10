package com.waben.stock.interfaces.pojo.query.organization;

import java.util.Date;

import com.waben.stock.interfaces.enums.CapitalFlowType;
import com.waben.stock.interfaces.pojo.query.PageAndSortQuery;

/**
 * 交易流水记录查询
 * 
 * @author sl
 *
 */
public class TradingFowQuery extends PageAndSortQuery {

	/**
	 * 代理商ID
	 */
	private Long orgId;

	/**
	 * 客户姓名
	 */
	private String customerName;

	/**
	 * 交易账号
	 */
	private String tradingNumber;

	/**
	 * 交易开始时间
	 */
	private Date occurrenceTimeStart;

	/**
	 * 交易结束时间
	 */
	private Date occurrenceTimeEnd;

	/**
	 * 业务类型
	 */
	private CapitalFlowType type;

	/**
	 * 股票代码
	 */
	private String stockCode;

	/**
	 * 所属代理商代码/名称
	 */
	private String agentCodeName;

	/**
	 * 查询类型
	 * <p>
	 * 1 平台登录、查询所有数据，2 代理商登录、查询自己数据+直属代理商数据
	 * </p>
	 */
//	private Integer queryType;

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

	public Date getOccurrenceTimeStart() {
		return occurrenceTimeStart;
	}

	public void setOccurrenceTimeStart(Date occurrenceTimeStart) {
		this.occurrenceTimeStart = occurrenceTimeStart;
	}

	public Date getOccurrenceTimeEnd() {
		return occurrenceTimeEnd;
	}

	public void setOccurrenceTimeEnd(Date occurrenceTimeEnd) {
		this.occurrenceTimeEnd = occurrenceTimeEnd;
	}

	public CapitalFlowType getType() {
		return type;
	}

	public void setType(CapitalFlowType type) {
		this.type = type;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getAgentCodeName() {
		return agentCodeName;
	}

	public void setAgentCodeName(String agentCodeName) {
		this.agentCodeName = agentCodeName;
	}

	/*public Integer getQueryType() {
		return queryType;
	}

	public void setQueryType(Integer queryType) {
		this.queryType = queryType;
	}*/

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

}
