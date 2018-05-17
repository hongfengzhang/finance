package com.waben.stock.interfaces.dto.organization;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 机构账户流水
 * 
 * @author luomengan
 *
 */
public class OrganizationAccountFlowWithTradeInfoDto {

	private Long id;
	/**
	 * 金额
	 */
	private BigDecimal amount;
	/**
	 * 流水号
	 */
	private String flowNo;
	/**
	 * 产生时间
	 */
	private Date occurrenceTime;
	/**
	 * 原始资金
	 */
	private BigDecimal originAmount;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 对应的资源ID
	 */
	private Long resourceId;
	/**
	 * 对应的资源交易单号
	 */
	private String resourceTradeNo;
	/**
	 * 分成配置类型
	 */
	private Integer resourceType;
	/**
	 * 流水类型
	 */
	private Integer type;
	/**
	 * 对应的机构ID
	 */
	private Long orgId;
	/**
	 * 发布人ID
	 */
	private Long publisherId;
	/**
	 * 发布人手机号
	 */
	private String publisherPhone;
	/**
	 * 股票代码
	 */
	private String stockCode;
	/**
	 * 股票名称
	 */
	private String stockName;
	/**
	 * 发布人ID（点买记录）
	 */
	private Long bPublisherId;
	/**
	 * 发布人手机号（点买记录）
	 */
	private String bPublisherPhone;
	/**
	 * 股票代码（点买记录）
	 */
	private String bStockCode;
	/**
	 * 股票名称（点买记录）
	 */
	private String bStockName;
	/**
	 * 发布人ID（期权交易）
	 */
	private Long sPublisherId;
	/**
	 * 发布人手机号（期权交易）
	 */
	private String sPublisherPhone;
	/**
	 * 股票代码（期权交易）
	 */
	private String sStockCode;
	/**
	 * 股票名称（期权交易）
	 */
	private String sStockName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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

	public BigDecimal getOriginAmount() {
		return originAmount;
	}

	public void setOriginAmount(BigDecimal originAmount) {
		this.originAmount = originAmount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceTradeNo() {
		return resourceTradeNo;
	}

	public void setResourceTradeNo(String resourceTradeNo) {
		this.resourceTradeNo = resourceTradeNo;
	}

	public Integer getResourceType() {
		return resourceType;
	}

	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getPublisherId() {
		if(resourceType != null && resourceType.intValue() == 1) {
			return bPublisherId;
		} else if(resourceType != null && resourceType.intValue() == 3) {
			return sPublisherId;
		}
		return publisherId;
	}

	public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
	}

	public String getPublisherPhone() {
		if(resourceType != null && resourceType.intValue() == 1) {
			return bPublisherPhone;
		} else if(resourceType != null && resourceType.intValue() == 3) {
			return sPublisherPhone;
		}
		return publisherPhone;
	}

	public void setPublisherPhone(String publisherPhone) {
		this.publisherPhone = publisherPhone;
	}

	public String getStockCode() {
		if(resourceType != null && resourceType.intValue() == 1) {
			return bStockCode;
		} else if(resourceType != null && resourceType.intValue() == 3) {
			return sStockCode;
		}
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getStockName() {
		if(resourceType != null && resourceType.intValue() == 1) {
			return bStockName;
		} else if(resourceType != null && resourceType.intValue() == 3) {
			return sStockName;
		}
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public Long getbPublisherId() {
		return bPublisherId;
	}

	public void setbPublisherId(Long bPublisherId) {
		this.bPublisherId = bPublisherId;
	}

	public String getbPublisherPhone() {
		return bPublisherPhone;
	}

	public void setbPublisherPhone(String bPublisherPhone) {
		this.bPublisherPhone = bPublisherPhone;
	}

	public String getbStockCode() {
		return bStockCode;
	}

	public void setbStockCode(String bStockCode) {
		this.bStockCode = bStockCode;
	}

	public String getbStockName() {
		return bStockName;
	}

	public void setbStockName(String bStockName) {
		this.bStockName = bStockName;
	}

	public Long getsPublisherId() {
		return sPublisherId;
	}

	public void setsPublisherId(Long sPublisherId) {
		this.sPublisherId = sPublisherId;
	}

	public String getsPublisherPhone() {
		return sPublisherPhone;
	}

	public void setsPublisherPhone(String sPublisherPhone) {
		this.sPublisherPhone = sPublisherPhone;
	}

	public String getsStockCode() {
		return sStockCode;
	}

	public void setsStockCode(String sStockCode) {
		this.sStockCode = sStockCode;
	}

	public String getsStockName() {
		return sStockName;
	}

	public void setsStockName(String sStockName) {
		this.sStockName = sStockName;
	}

}
