/**
 * DataBaseWebServiceSoap_BindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.winsafe.drp.ws;

public class DataBaseWebServiceSoap_BindingSkeleton implements com.winsafe.drp.ws.DataBaseWebServiceSoap_PortType, org.apache.axis.wsdl.Skeleton {
    private com.winsafe.drp.ws.DataBaseWebServiceSoap_PortType impl;
    private static java.util.Map _myOperations = new java.util.Hashtable();
    private static java.util.Collection _myOperationsList = new java.util.ArrayList();

    /**
    * Returns List of OperationDesc objects with this name
    */
    public static java.util.List getOperationDescByName(java.lang.String methodName) {
        return (java.util.List)_myOperations.get(methodName);
    }

    /**
    * Returns Collection of OperationDescs
    */
    public static java.util.Collection getOperationDescs() {
        return _myOperationsList;
    }

    static {
        org.apache.axis.description.OperationDesc _oper;
        org.apache.axis.description.FaultDesc _fault;
        org.apache.axis.description.ParameterDesc [] _params;
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://tempuri.org/", "strFWNumber"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getFWAddInfo", _params, new javax.xml.namespace.QName("http://tempuri.org/", "GetFWAddInfoResult"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://tempuri.org/", ">>GetFWAddInfoResponse>GetFWAddInfoResult"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://tempuri.org/", "GetFWAddInfo"));
        _oper.setSoapAction("http://tempuri.org/GetFWAddInfo");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getFWAddInfo") == null) {
            _myOperations.put("getFWAddInfo", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getFWAddInfo")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://tempuri.org/", "strSql"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getDataSet", _params, new javax.xml.namespace.QName("http://tempuri.org/", "GetDataSetResult"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://tempuri.org/", ">>GetDataSetResponse>GetDataSetResult"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://tempuri.org/", "GetDataSet"));
        _oper.setSoapAction("http://tempuri.org/GetDataSet");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getDataSet") == null) {
            _myOperations.put("getDataSet", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getDataSet")).add(_oper);
    }

    public DataBaseWebServiceSoap_BindingSkeleton() {
        this.impl = new com.winsafe.drp.ws.DataBaseWebServiceSoap_BindingImpl();
    }

    public DataBaseWebServiceSoap_BindingSkeleton(com.winsafe.drp.ws.DataBaseWebServiceSoap_PortType impl) {
        this.impl = impl;
    }
    public com.winsafe.drp.ws.GetFWAddInfoResponseGetFWAddInfoResult getFWAddInfo(java.lang.String strFWNumber) throws java.rmi.RemoteException
    {
        com.winsafe.drp.ws.GetFWAddInfoResponseGetFWAddInfoResult ret = impl.getFWAddInfo(strFWNumber);
        return ret;
    }

    public com.winsafe.drp.ws.GetDataSetResponseGetDataSetResult getDataSet(java.lang.String strSql) throws java.rmi.RemoteException
    {
        com.winsafe.drp.ws.GetDataSetResponseGetDataSetResult ret = impl.getDataSet(strSql);
        return ret;
    }

}
