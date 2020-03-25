/**
 * DataBaseWebServiceSoap_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.winsafe.drp.ws;

public interface DataBaseWebServiceSoap_PortType extends java.rmi.Remote {
    public com.winsafe.drp.ws.GetFWAddInfoResponseGetFWAddInfoResult getFWAddInfo(java.lang.String strFWNumber) throws java.rmi.RemoteException;
    public com.winsafe.drp.ws.GetDataSetResponseGetDataSetResult getDataSet(java.lang.String strSql) throws java.rmi.RemoteException;
}
