/**
 * DataBaseWebService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.winsafe.drp.ws;

public interface DataBaseWebService extends javax.xml.rpc.Service {
    public java.lang.String getDataBaseWebServiceSoap12Address();

    public com.winsafe.drp.ws.DataBaseWebServiceSoap_PortType getDataBaseWebServiceSoap12() throws javax.xml.rpc.ServiceException;

    public com.winsafe.drp.ws.DataBaseWebServiceSoap_PortType getDataBaseWebServiceSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public java.lang.String getDataBaseWebServiceSoapAddress();

    public com.winsafe.drp.ws.DataBaseWebServiceSoap_PortType getDataBaseWebServiceSoap() throws javax.xml.rpc.ServiceException;

    public com.winsafe.drp.ws.DataBaseWebServiceSoap_PortType getDataBaseWebServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
