/**
 * QueryResponseBean.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.waben.stock.applayer.tactics.payapi.fastmoney.bean;

public class QueryResponseBean implements java.io.Serializable {

	private double amount;

	private String dealBeginDate;

	private String dealEndDate;

	private double dealFee;

	private String dealId;
	/**
	 * 交易状态
	 * <ul>
	 * <li>111 代表成功</li>
	 * <li>101 代表进行中</li>
	 * <li>112 代表失败</li>
	 * <li>114 已退款</li>
	 * </ul>
	 */
	private String dealStatus;

	private String failureCause;

	private String orderId;

	private String queryType;

	private boolean resultFlag;

	public QueryResponseBean() {
	}

	public QueryResponseBean(double amount, String dealBeginDate, String dealEndDate, double dealFee, String dealId,
			String dealStatus, String failureCause, String orderId, String queryType, boolean resultFlag) {
		this.amount = amount;
		this.dealBeginDate = dealBeginDate;
		this.dealEndDate = dealEndDate;
		this.dealFee = dealFee;
		this.dealId = dealId;
		this.dealStatus = dealStatus;
		this.failureCause = failureCause;
		this.orderId = orderId;
		this.queryType = queryType;
		this.resultFlag = resultFlag;
	}

	/**
	 * Gets the amount value for this QueryResponseBean.
	 *
	 * @return amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * Sets the amount value for this QueryResponseBean.
	 *
	 * @param amount
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * Gets the dealBeginDate value for this QueryResponseBean.
	 *
	 * @return dealBeginDate
	 */
	public String getDealBeginDate() {
		return dealBeginDate;
	}

	/**
	 * Sets the dealBeginDate value for this QueryResponseBean.
	 *
	 * @param dealBeginDate
	 */
	public void setDealBeginDate(String dealBeginDate) {
		this.dealBeginDate = dealBeginDate;
	}

	/**
	 * Gets the dealEndDate value for this QueryResponseBean.
	 *
	 * @return dealEndDate
	 */
	public String getDealEndDate() {
		return dealEndDate;
	}

	/**
	 * Sets the dealEndDate value for this QueryResponseBean.
	 *
	 * @param dealEndDate
	 */
	public void setDealEndDate(String dealEndDate) {
		this.dealEndDate = dealEndDate;
	}

	/**
	 * Gets the dealFee value for this QueryResponseBean.
	 *
	 * @return dealFee
	 */
	public double getDealFee() {
		return dealFee;
	}

	/**
	 * Sets the dealFee value for this QueryResponseBean.
	 *
	 * @param dealFee
	 */
	public void setDealFee(double dealFee) {
		this.dealFee = dealFee;
	}

	/**
	 * Gets the dealId value for this QueryResponseBean.
	 *
	 * @return dealId
	 */
	public String getDealId() {
		return dealId;
	}

	/**
	 * Sets the dealId value for this QueryResponseBean.
	 *
	 * @param dealId
	 */
	public void setDealId(String dealId) {
		this.dealId = dealId;
	}

	/**
	 * Gets the dealStatus value for this QueryResponseBean.
	 *
	 * @return dealStatus
	 */
	public String getDealStatus() {
		return dealStatus;
	}

	/**
	 * Sets the dealStatus value for this QueryResponseBean.
	 *
	 * @param dealStatus
	 */
	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}

	/**
	 * Gets the failureCause value for this QueryResponseBean.
	 *
	 * @return failureCause
	 */
	public String getFailureCause() {
		return failureCause;
	}

	/**
	 * Sets the failureCause value for this QueryResponseBean.
	 *
	 * @param failureCause
	 */
	public void setFailureCause(String failureCause) {
		this.failureCause = failureCause;
	}

	/**
	 * Gets the orderId value for this QueryResponseBean.
	 *
	 * @return orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * Sets the orderId value for this QueryResponseBean.
	 *
	 * @param orderId
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * Gets the queryType value for this QueryResponseBean.
	 *
	 * @return queryType
	 */
	public String getQueryType() {
		return queryType;
	}

	/**
	 * Sets the queryType value for this QueryResponseBean.
	 *
	 * @param queryType
	 */
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	/**
	 * Gets the resultFlag value for this QueryResponseBean.
	 *
	 * @return resultFlag
	 */
	public boolean isResultFlag() {
		return resultFlag;
	}

	/**
	 * Sets the resultFlag value for this QueryResponseBean.
	 *
	 * @param resultFlag
	 */
	public void setResultFlag(boolean resultFlag) {
		this.resultFlag = resultFlag;
	}

	private Object __equalsCalc = null;

	public synchronized boolean equals(Object obj) {
		if (!(obj instanceof QueryResponseBean))
			return false;
		QueryResponseBean other = (QueryResponseBean) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = true && this.amount == other.getAmount()
				&& ((this.dealBeginDate == null && other.getDealBeginDate() == null)
						|| (this.dealBeginDate != null && this.dealBeginDate.equals(other.getDealBeginDate())))
				&& ((this.dealEndDate == null && other.getDealEndDate() == null)
						|| (this.dealEndDate != null && this.dealEndDate.equals(other.getDealEndDate())))
				&& this.dealFee == other.getDealFee()
				&& ((this.dealId == null && other.getDealId() == null)
						|| (this.dealId != null && this.dealId.equals(other.getDealId())))
				&& ((this.dealStatus == null && other.getDealStatus() == null)
						|| (this.dealStatus != null && this.dealStatus.equals(other.getDealStatus())))
				&& ((this.failureCause == null && other.getFailureCause() == null)
						|| (this.failureCause != null && this.failureCause.equals(other.getFailureCause())))
				&& ((this.orderId == null && other.getOrderId() == null)
						|| (this.orderId != null && this.orderId.equals(other.getOrderId())))
				&& ((this.queryType == null && other.getQueryType() == null)
						|| (this.queryType != null && this.queryType.equals(other.getQueryType())))
				&& this.resultFlag == other.isResultFlag();
		__equalsCalc = null;
		return _equals;
	}

	private boolean __hashCodeCalc = false;

	public synchronized int hashCode() {
		if (__hashCodeCalc) {
			return 0;
		}
		__hashCodeCalc = true;
		int _hashCode = 1;
		_hashCode += new Double(getAmount()).hashCode();
		if (getDealBeginDate() != null) {
			_hashCode += getDealBeginDate().hashCode();
		}
		if (getDealEndDate() != null) {
			_hashCode += getDealEndDate().hashCode();
		}
		_hashCode += new Double(getDealFee()).hashCode();
		if (getDealId() != null) {
			_hashCode += getDealId().hashCode();
		}
		if (getDealStatus() != null) {
			_hashCode += getDealStatus().hashCode();
		}
		if (getFailureCause() != null) {
			_hashCode += getFailureCause().hashCode();
		}
		if (getOrderId() != null) {
			_hashCode += getOrderId().hashCode();
		}
		if (getQueryType() != null) {
			_hashCode += getQueryType().hashCode();
		}
		_hashCode += (isResultFlag() ? Boolean.TRUE : Boolean.FALSE).hashCode();
		__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
			QueryResponseBean.class, true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("http://complatible.dto.domain.seashell.bill99.com",
				"QueryResponseBean"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("amount");
		elemField.setXmlName(new javax.xml.namespace.QName("", "amount"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("dealBeginDate");
		elemField.setXmlName(new javax.xml.namespace.QName("", "dealBeginDate"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("dealEndDate");
		elemField.setXmlName(new javax.xml.namespace.QName("", "dealEndDate"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("dealFee");
		elemField.setXmlName(new javax.xml.namespace.QName("", "dealFee"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("dealId");
		elemField.setXmlName(new javax.xml.namespace.QName("", "dealId"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("dealStatus");
		elemField.setXmlName(new javax.xml.namespace.QName("", "dealStatus"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("failureCause");
		elemField.setXmlName(new javax.xml.namespace.QName("", "failureCause"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("orderId");
		elemField.setXmlName(new javax.xml.namespace.QName("", "orderId"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("queryType");
		elemField.setXmlName(new javax.xml.namespace.QName("", "queryType"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("resultFlag");
		elemField.setXmlName(new javax.xml.namespace.QName("", "resultFlag"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
	}

	/**
	 * Return type metadata object
	 */
	public static org.apache.axis.description.TypeDesc getTypeDesc() {
		return typeDesc;
	}

	/**
	 * Get Custom Serializer
	 */
	public static org.apache.axis.encoding.Serializer getSerializer(String mechType, Class _javaType,
			javax.xml.namespace.QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanSerializer(_javaType, _xmlType, typeDesc);
	}

	/**
	 * Get Custom Deserializer
	 */
	public static org.apache.axis.encoding.Deserializer getDeserializer(String mechType, Class _javaType,
			javax.xml.namespace.QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanDeserializer(_javaType, _xmlType, typeDesc);
	}

}
