/**
 * BatchPayService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.waben.stock.applayer.tactics.payapi.fastmoney.utils.BatchPayWS;

public interface BatchPayService extends javax.xml.rpc.Service {
    public String getBatchPayWSAddress();

    public BatchPay getBatchPayWS() throws javax.xml.rpc.ServiceException;

    public BatchPay getBatchPayWS(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
