/**
 * DataBaseWebServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.winsafe.drp.ws;

public class DataBaseWebServiceLocator extends org.apache.axis.client.Service implements com.winsafe.drp.ws.DataBaseWebService {

    public DataBaseWebServiceLocator() {
    }


    public DataBaseWebServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public DataBaseWebServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for DataBaseWebServiceSoap12
    private java.lang.String DataBaseWebServiceSoap12_address = "http://www.winsafe.cn/fwapi/DataBaseWebService.asmx";

    public java.lang.String getDataBaseWebServiceSoap12Address() {
        return DataBaseWebServiceSoap12_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String DataBaseWebServiceSoap12WSDDServiceName = "DataBaseWebServiceSoap12";

    public java.lang.String getDataBaseWebServiceSoap12WSDDServiceName() {
        return DataBaseWebServiceSoap12WSDDServiceName;
    }

    public void setDataBaseWebServiceSoap12WSDDServiceName(java.lang.String name) {
        DataBaseWebServiceSoap12WSDDServiceName = name;
    }

    public com.winsafe.drp.ws.DataBaseWebServiceSoap_PortType getDataBaseWebServiceSoap12() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(DataBaseWebServiceSoap12_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getDataBaseWebServiceSoap12(endpoint);
    }

    public com.winsafe.drp.ws.DataBaseWebServiceSoap_PortType getDataBaseWebServiceSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.winsafe.drp.ws.DataBaseWebServiceSoap12Stub _stub = new com.winsafe.drp.ws.DataBaseWebServiceSoap12Stub(portAddress, this);
            _stub.setPortName(getDataBaseWebServiceSoap12WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setDataBaseWebServiceSoap12EndpointAddress(java.lang.String address) {
        DataBaseWebServiceSoap12_address = address;
    }


    // Use to get a proxy class for DataBaseWebServiceSoap
    private java.lang.String DataBaseWebServiceSoap_address = "http://www.winsafe.cn/fwapi/DataBaseWebService.asmx";

    public java.lang.String getDataBaseWebServiceSoapAddress() {
        return DataBaseWebServiceSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String DataBaseWebServiceSoapWSDDServiceName = "DataBaseWebServiceSoap";

    public java.lang.String getDataBaseWebServiceSoapWSDDServiceName() {
        return DataBaseWebServiceSoapWSDDServiceName;
    }

    public void setDataBaseWebServiceSoapWSDDServiceName(java.lang.String name) {
        DataBaseWebServiceSoapWSDDServiceName = name;
    }

    public com.winsafe.drp.ws.DataBaseWebServiceSoap_PortType getDataBaseWebServiceSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(DataBaseWebServiceSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getDataBaseWebServiceSoap(endpoint);
    }

    public com.winsafe.drp.ws.DataBaseWebServiceSoap_PortType getDataBaseWebServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.winsafe.drp.ws.DataBaseWebServiceSoap_BindingStub _stub = new com.winsafe.drp.ws.DataBaseWebServiceSoap_BindingStub(portAddress, this);
            _stub.setPortName(getDataBaseWebServiceSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setDataBaseWebServiceSoapEndpointAddress(java.lang.String address) {
        DataBaseWebServiceSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     * This service has multiple ports for a given interface;
     * the proxy implementation returned may be indeterminate.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.winsafe.drp.ws.DataBaseWebServiceSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.winsafe.drp.ws.DataBaseWebServiceSoap12Stub _stub = new com.winsafe.drp.ws.DataBaseWebServiceSoap12Stub(new java.net.URL(DataBaseWebServiceSoap12_address), this);
                _stub.setPortName(getDataBaseWebServiceSoap12WSDDServiceName());
                return _stub;
            }
            if (com.winsafe.drp.ws.DataBaseWebServiceSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.winsafe.drp.ws.DataBaseWebServiceSoap_BindingStub _stub = new com.winsafe.drp.ws.DataBaseWebServiceSoap_BindingStub(new java.net.URL(DataBaseWebServiceSoap_address), this);
                _stub.setPortName(getDataBaseWebServiceSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("DataBaseWebServiceSoap12".equals(inputPortName)) {
            return getDataBaseWebServiceSoap12();
        }
        else if ("DataBaseWebServiceSoap".equals(inputPortName)) {
            return getDataBaseWebServiceSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "DataBaseWebService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "DataBaseWebServiceSoap12"));
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "DataBaseWebServiceSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("DataBaseWebServiceSoap12".equals(portName)) {
            setDataBaseWebServiceSoap12EndpointAddress(address);
        }
        else 
if ("DataBaseWebServiceSoap".equals(portName)) {
            setDataBaseWebServiceSoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
