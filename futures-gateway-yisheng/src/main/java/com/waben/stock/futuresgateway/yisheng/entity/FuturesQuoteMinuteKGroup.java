package com.waben.stock.futuresgateway.yisheng.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * 行情-分钟K组合
 * 
 * @author luomengan
 *
 */
@Entity
@Table(name = "f_gateway_futures_es_quote_minutek_group")
public class FuturesQuoteMinuteKGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/** 品种ID */
	private Long commodityId;
	/** 品种编号 */
	private String commodityNo;
	/** 合约ID */
	private Long contractId;
	/** 合约编号 */
	private String contractNo;
	/** 开始时间 */
	private Date startTime;
	/** 结束时间 */
	private Date endTime;
	/** 组合数据，json数组存放分钟K */
	@Type(type = "text")
	private String groupData;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(Long commodityId) {
		this.commodityId = commodityId;
	}

	public String getCommodityNo() {
		return commodityNo;
	}

	public void setCommodityNo(String commodityNo) {
		this.commodityNo = commodityNo;
	}

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getGroupData() {
		return groupData;
	}

	public void setGroupData(String groupData) {
		this.groupData = groupData;
	}

}
