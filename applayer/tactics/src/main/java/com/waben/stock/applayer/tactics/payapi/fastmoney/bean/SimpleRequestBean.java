/**
 * SimpleRequestBean.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.waben.stock.applayer.tactics.payapi.fastmoney.bean;

public class SimpleRequestBean  implements java.io.Serializable {
    private double amount;

    private String correctName;

    private String creditContact;

    private String creditName;

    private String currencyCode;

    private String description;

    private String mac;

    private String orderId;

    private String tempAccount;

    public SimpleRequestBean() {
    }

    public SimpleRequestBean(
           double amount,
           String correctName,
           String creditContact,
           String creditName,
           String currencyCode,
           String description,
           String mac,
           String orderId,
           String tempAccount) {
           this.amount = amount;
           this.correctName = correctName;
           this.creditContact = creditContact;
           this.creditName = creditName;
           this.currencyCode = currencyCode;
           this.description = description;
           this.mac = mac;
           this.orderId = orderId;
           this.tempAccount = tempAccount;
    }


    /**
     * Gets the amount value for this SimpleRequestBean.
     *
     * @return amount
     */
    public double getAmount() {
        return amount;
    }


    /**
     * Sets the amount value for this SimpleRequestBean.
     *
     * @param amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }


    /**
     * Gets the correctName value for this SimpleRequestBean.
     *
     * @return correctName
     */
    public String getCorrectName() {
        return correctName;
    }


    /**
     * Sets the correctName value for this SimpleRequestBean.
     *
     * @param correctName
     */
    public void setCorrectName(String correctName) {
        this.correctName = correctName;
    }


    /**
     * Gets the creditContact value for this SimpleRequestBean.
     *
     * @return creditContact
     */
    public String getCreditContact() {
        return creditContact;
    }


    /**
     * Sets the creditContact value for this SimpleRequestBean.
     *
     * @param creditContact
     */
    public void setCreditContact(String creditContact) {
        this.creditContact = creditContact;
    }


    /**
     * Gets the creditName value for this SimpleRequestBean.
     *
     * @return creditName
     */
    public String getCreditName() {
        return creditName;
    }


    /**
     * Sets the creditName value for this SimpleRequestBean.
     *
     * @param creditName
     */
    public void setCreditName(String creditName) {
        this.creditName = creditName;
    }


    /**
     * Gets the currencyCode value for this SimpleRequestBean.
     *
     * @return currencyCode
     */
    public String getCurrencyCode() {
        return currencyCode;
    }


    /**
     * Sets the currencyCode value for this SimpleRequestBean.
     *
     * @param currencyCode
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }


    /**
     * Gets the description value for this SimpleRequestBean.
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this SimpleRequestBean.
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * Gets the mac value for this SimpleRequestBean.
     *
     * @return mac
     */
    public String getMac() {
        return mac;
    }


    /**
     * Sets the mac value for this SimpleRequestBean.
     *
     * @param mac
     */
    public void setMac(String mac) {
        this.mac = mac;
    }


    /**
     * Gets the orderId value for this SimpleRequestBean.
     *
     * @return orderId
     */
    public String getOrderId() {
        return orderId;
    }


    /**
     * Sets the orderId value for this SimpleRequestBean.
     *
     * @param orderId
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    /**
     * Gets the tempAccount value for this SimpleRequestBean.
     *
     * @return tempAccount
     */
    public String getTempAccount() {
        return tempAccount;
    }


    /**
     * Sets the tempAccount value for this SimpleRequestBean.
     *
     * @param tempAccount
     */
    public void setTempAccount(String tempAccount) {
        this.tempAccount = tempAccount;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof SimpleRequestBean)) return false;
        SimpleRequestBean other = (SimpleRequestBean) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
            this.amount == other.getAmount() &&
            ((this.correctName==null && other.getCorrectName()==null) ||
             (this.correctName!=null &&
              this.correctName.equals(other.getCorrectName()))) &&
            ((this.creditContact==null && other.getCreditContact()==null) ||
             (this.creditContact!=null &&
              this.creditContact.equals(other.getCreditContact()))) &&
            ((this.creditName==null && other.getCreditName()==null) ||
             (this.creditName!=null &&
              this.creditName.equals(other.getCreditName()))) &&
            ((this.currencyCode==null && other.getCurrencyCode()==null) ||
             (this.currencyCode!=null &&
              this.currencyCode.equals(other.getCurrencyCode()))) &&
            ((this.description==null && other.getDescription()==null) ||
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.mac==null && other.getMac()==null) ||
             (this.mac!=null &&
              this.mac.equals(other.getMac()))) &&
            ((this.orderId==null && other.getOrderId()==null) ||
             (this.orderId!=null &&
              this.orderId.equals(other.getOrderId()))) &&
            ((this.tempAccount==null && other.getTempAccount()==null) ||
             (this.tempAccount!=null &&
              this.tempAccount.equals(other.getTempAccount())));
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
        if (getCorrectName() != null) {
            _hashCode += getCorrectName().hashCode();
        }
        if (getCreditContact() != null) {
            _hashCode += getCreditContact().hashCode();
        }
        if (getCreditName() != null) {
            _hashCode += getCreditName().hashCode();
        }
        if (getCurrencyCode() != null) {
            _hashCode += getCurrencyCode().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getMac() != null) {
            _hashCode += getMac().hashCode();
        }
        if (getOrderId() != null) {
            _hashCode += getOrderId().hashCode();
        }
        if (getTempAccount() != null) {
            _hashCode += getTempAccount().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SimpleRequestBean.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://complatible.dto.domain.seashell.bill99.com", "SimpleRequestBean"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "amount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("correctName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "correctName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creditContact");
        elemField.setXmlName(new javax.xml.namespace.QName("", "creditContact"));
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
        elemField.setFieldName("currencyCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "currencyCode"));
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
        elemField.setFieldName("tempAccount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tempAccount"));
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
    public static org.apache.axis.encoding.Serializer getSerializer(
           String mechType,
           Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           String mechType,
           Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
