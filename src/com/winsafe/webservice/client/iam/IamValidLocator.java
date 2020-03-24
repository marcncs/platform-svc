/**
 * IamValidLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.winsafe.webservice.client.iam;

import java.util.Properties;

import com.winsafe.drp.util.JProperties;

public class IamValidLocator extends org.apache.axis.client.Service implements com.winsafe.webservice.client.iam.IamValid {

    public IamValidLocator() {
    }


    public IamValidLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public IamValidLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for IamValidSoap
    private java.lang.String IamValidSoap_address = "https://bcnshgs0198.ap.bayer.cnb/IAMValidation/IamValid.asmx";

    public java.lang.String getIamValidSoapAddress() {
        return IamValidSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String IamValidSoapWSDDServiceName = "IamValidSoap";

    public java.lang.String getIamValidSoapWSDDServiceName() {
        return IamValidSoapWSDDServiceName;
    }

    public void setIamValidSoapWSDDServiceName(java.lang.String name) {
        IamValidSoapWSDDServiceName = name;
    }

    public com.winsafe.webservice.client.iam.IamValidSoap getIamValidSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
        	Properties systemPro = JProperties.loadProperties("system.properties", JProperties.BY_CLASSLOADER);
        	if(systemPro.containsKey("IAMWsdLUrl")) {
        		IamValidSoap_address=systemPro.getProperty("IAMWsdLUrl");
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
        try {
            endpoint = new java.net.URL(IamValidSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getIamValidSoap(endpoint);
    }

    public com.winsafe.webservice.client.iam.IamValidSoap getIamValidSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.winsafe.webservice.client.iam.IamValidSoapStub _stub = new com.winsafe.webservice.client.iam.IamValidSoapStub(portAddress, this);
            _stub.setPortName(getIamValidSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setIamValidSoapEndpointAddress(java.lang.String address) {
        IamValidSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.winsafe.webservice.client.iam.IamValidSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                com.winsafe.webservice.client.iam.IamValidSoapStub _stub = new com.winsafe.webservice.client.iam.IamValidSoapStub(new java.net.URL(IamValidSoap_address), this);
                _stub.setPortName(getIamValidSoapWSDDServiceName());
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
        if ("IamValidSoap".equals(inputPortName)) {
            return getIamValidSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "IamValid");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "IamValidSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("IamValidSoap".equals(portName)) {
            setIamValidSoapEndpointAddress(address);
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
