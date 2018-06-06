/**
 * BankRequestBean.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.waben.stock.applayer.tactics.payapi.fastmoney.bean;

public class BankRequestBean implements java.io.Serializable {

	/**
	 * 金额
	 * <p>
	 * 单位分
	 * </p>
	 */
	private double amount;
	/**
	 *  银行卡号
	 */
	private String bankCardNumber;
	/**
	 * 银行名称
	 */
	private String bankName;
	/**
	 * 收款人姓名
	 */
	private String creditName;
	/**
	 * 交易备注
	 */
	private String description;
	/**
	 * 开户行
	 */
	private String kaihuhang;
	/**
	 * 订单号
	 */
	private String orderId;
	/**
	 * 城市
	 */
	private String province_city;

	private String mac;

	public BankRequestBean() {
	}

	public BankRequestBean(double amount, String bankCardNumber, String bankName, String creditName, String description,
			String kaihuhang, String mac, String orderId, String province_city) {
		this.amount = amount;
		this.bankCardNumber = bankCardNumber;
		this.bankName = bankName;
		this.creditName = creditName;
		this.description = description;
		this.kaihuhang = kaihuhang;
		this.mac = mac;
		this.orderId = orderId;
		this.province_city = province_city;
	}

	/**
	 * Gets the amount value for this BankRequestBean.
	 *
	 * @return amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * Sets the amount value for this BankRequestBean.
	 *
	 * @param amount
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * Gets the bankCardNumber value for this BankRequestBean.
	 *
	 * @return bankCardNumber
	 */
	public String getBankCardNumber() {
		return bankCardNumber;
	}

	/**
	 * Sets the bankCardNumber value for this BankRequestBean.
	 *
	 * @param bankCardNumber
	 */
	public void setBankCardNumber(String bankCardNumber) {
		this.bankCardNumber = bankCardNumber;
	}

	/**
	 * Gets the bankName value for this BankRequestBean.
	 *
	 * @return bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * Sets the bankName value for this BankRequestBean.
	 *
	 * @param bankName
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * Gets the creditName value for this BankRequestBean.
	 *
	 * @return creditName
	 */
	public String getCreditName() {
		return creditName;
	}

	/**
	 * Sets the creditName value for this BankRequestBean.
	 *
	 * @param creditName
	 */
	public void setCreditName(String creditName) {
		this.creditName = creditName;
	}

	/**
	 * Gets the description value for this BankRequestBean.
	 *
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description value for this BankRequestBean.
	 *
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the kaihuhang value for this BankRequestBean.
	 *
	 * @return kaihuhang
	 */
	public String getKaihuhang() {
		return kaihuhang;
	}

	/**
	 * Sets the kaihuhang value for this BankRequestBean.
	 *
	 * @param kaihuhang
	 */
	public void setKaihuhang(String kaihuhang) {
		this.kaihuhang = kaihuhang;
	}

	/**
	 * Gets the mac value for this BankRequestBean.
	 *
	 * @return mac
	 */
	public String getMac() {
		return mac;
	}

	/**
	 * Sets the mac value for this BankRequestBean.
	 *
	 * @param mac
	 */
	public void setMac(String mac) {
		this.mac = mac;
	}

	/**
	 * Gets the orderId value for this BankRequestBean.
	 *
	 * @return orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * Sets the orderId value for this BankRequestBean.
	 *
	 * @param orderId
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * Gets the province_city value for this BankRequestBean.
	 *
	 * @return province_city
	 */
	public String getProvince_city() {
		return province_city;
	}

	/**
	 * Sets the province_city value for this BankRequestBean.
	 *
	 * @param province_city
	 */
	public void setProvince_city(String province_city) {
		this.province_city = province_city;
	}

	private Object __equalsCalc = null;

	public synchronized boolean equals(Object obj) {
		if (!(obj instanceof BankRequestBean))
			return false;
		BankRequestBean other = (BankRequestBean) obj;
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
				&& ((this.bankCardNumber == null && other.getBankCardNumber() == null)
						|| (this.bankCardNumber != null && this.bankCardNumber.equals(other.getBankCardNumber())))
				&& ((this.bankName == null && other.getBankName() == null)
						|| (this.bankName != null && this.bankName.equals(other.getBankName())))
				&& ((this.creditName == null && other.getCreditName() == null)
						|| (this.creditName != null && this.creditName.equals(other.getCreditName())))
				&& ((this.description == null && other.getDescription() == null)
						|| (this.description != null && this.description.equals(other.getDescription())))
				&& ((this.kaihuhang == null && other.getKaihuhang() == null)
						|| (this.kaihuhang != null && this.kaihuhang.equals(other.getKaihuhang())))
				&& ((this.mac == null && other.getMac() == null)
						|| (this.mac != null && this.mac.equals(other.getMac())))
				&& ((this.orderId == null && other.getOrderId() == null)
						|| (this.orderId != null && this.orderId.equals(other.getOrderId())))
				&& ((this.province_city == null && other.getProvince_city() == null)
						|| (this.province_city != null && this.province_city.equals(other.getProvince_city())));
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
		if (getBankCardNumber() != null) {
			_hashCode += getBankCardNumber().hashCode();
		}
		if (getBankName() != null) {
			_hashCode += getBankName().hashCode();
		}
		if (getCreditName() != null) {
			_hashCode += getCreditName().hashCode();
		}
		if (getDescription() != null) {
			_hashCode += getDescription().hashCode();
		}
		if (getKaihuhang() != null) {
			_hashCode += getKaihuhang().hashCode();
		}
		if (getMac() != null) {
			_hashCode += getMac().hashCode();
		}
		if (getOrderId() != null) {
			_hashCode += getOrderId().hashCode();
		}
		if (getProvince_city() != null) {
			_hashCode += getProvince_city().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
			BankRequestBean.class, true);

	static {
		typeDesc.setXmlType(
				new javax.xml.namespace.QName("http://complatible.dto.domain.seashell.bill99.com", "BankRequestBean"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("amount");
		elemField.setXmlName(new javax.xml.namespace.QName("", "amount"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("bankCardNumber");
		elemField.setXmlName(new javax.xml.namespace.QName("", "bankCardNumber"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("bankName");
		elemField.setXmlName(new javax.xml.namespace.QName("", "bankName"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("creditName");
		elemField.setXmlName(new javax.xml.namespace.QName("", "creditName"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("description");
		elemField.setXmlName(new javax.xml.namespace.QName("", "description"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("kaihuhang");
		elemField.setXmlName(new javax.xml.namespace.QName("", "kaihuhang"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("mac");
		elemField.setXmlName(new javax.xml.namespace.QName("", "mac"));
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
		elemField.setFieldName("province_city");
		elemField.setXmlName(new javax.xml.namespace.QName("", "province_city"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
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
