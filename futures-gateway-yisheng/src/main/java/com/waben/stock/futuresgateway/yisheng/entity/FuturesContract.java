package com.waben.stock.futuresgateway.yisheng.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 期货合约
 * 
 * @author luomengan
 *
 */
@Entity
@Table(name = "f_gateway_futures_es_contract")
public class FuturesContract {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/** 合约编号 */
	private String contractNo;
	/** 合约名称 */
	private String contractName;
	/** 首次通知日期 */
	private String firstNoticeDate;
	/** 最后交易日期 */
	private String lastTradeDate;
	/** 合约到期日期 */
	private String contractExpDate;
	/** 品种ID */
	private Long commodityId;
	/** 品种编号 */
	private String commodityNo;
	/** 是否可用 */
	private Boolean enable;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getFirstNoticeDate() {
		return firstNoticeDate;
	}

	public void setFirstNoticeDate(String firstNoticeDate) {
		this.firstNoticeDate = firstNoticeDate;
	}

	public String getLastTradeDate() {
		return lastTradeDate;
	}

	public void setLastTradeDate(String lastTradeDate) {
		this.lastTradeDate = lastTradeDate;
	}

	public String getContractExpDate() {
		return contractExpDate;
	}

	public void setContractExpDate(String contractExpDate) {
		this.contractExpDate = contractExpDate;
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

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

}
