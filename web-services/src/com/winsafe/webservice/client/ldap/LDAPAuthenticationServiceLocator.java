/**
 * LDAPAuthenticationServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.winsafe.webservice.client.ldap;

import java.util.Properties;

import com.winsafe.drp.util.JProperties;

public class LDAPAuthenticationServiceLocator extends org.apache.axis.client.Service implements com.winsafe.webservice.client.ldap.LDAPAuthenticationService {

    public LDAPAuthenticationServiceLocator() {
    }


    public LDAPAuthenticationServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public LDAPAuthenticationServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for LDAPAuthenticationServiceSoap
    private java.lang.String LDAPAuthenticationServiceSoap_address = "https://bsgsgps0505.ap.bayer.cnb/LDAPAuthentication/LDAPAuthenticationService.asmx";

    public java.lang.String getLDAPAuthenticationServiceSoapAddress() {
        return LDAPAuthenticationServiceSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String LDAPAuthenticationServiceSoapWSDDServiceName = "LDAPAuthenticationServiceSoap";

    public java.lang.String getLDAPAuthenticationServiceSoapWSDDServiceName() {
        return LDAPAuthenticationServiceSoapWSDDServiceName;
    }

    public void setLDAPAuthenticationServiceSoapWSDDServiceName(java.lang.String name) {
        LDAPAuthenticationServiceSoapWSDDServiceName = name;
    }

    public com.winsafe.webservice.client.ldap.LDAPAuthenticationServiceSoap getLDAPAuthenticationServiceSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
        	try {
            	Properties systemPro = JProperties.loadProperties("system.properties", JProperties.BY_CLASSLOADER);
            	if(systemPro.containsKey("LDAPWsdLUrl")) {
            		LDAPAuthenticationServiceSoap_address=systemPro.getProperty("LDAPWsdLUrl");
            	}
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
            endpoint = new java.net.URL(LDAPAuthenticationServiceSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getLDAPAuthenticationServiceSoap(endpoint);
    }

    public com.winsafe.webservice.client.ldap.LDAPAuthenticationServiceSoap getLDAPAuthenticationServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.winsafe.webservice.client.ldap.LDAPAuthenticationServiceSoapStub _stub = new com.winsafe.webservice.client.ldap.LDAPAuthenticationServiceSoapStub(portAddress, this);
            _stub.setPortName(getLDAPAuthenticationServiceSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setLDAPAuthenticationServiceSoapEndpointAddress(java.lang.String address) {
        LDAPAuthenticationServiceSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.winsafe.webservice.client.ldap.LDAPAuthenticationServiceSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                com.winsafe.webservice.client.ldap.LDAPAuthenticationServiceSoapStub _stub = new com.winsafe.webservice.client.ldap.LDAPAuthenticationServiceSoapStub(new java.net.URL(LDAPAuthenticationServiceSoap_address), this);
                _stub.setPortName(getLDAPAuthenticationServiceSoapWSDDServiceName());
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
        if ("LDAPAuthenticationServiceSoap".equals(inputPortName)) {
            return getLDAPAuthenticationServiceSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "LDAPAuthenticationService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "LDAPAuthenticationServiceSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("LDAPAuthenticationServiceSoap".equals(portName)) {
            setLDAPAuthenticationServiceSoapEndpointAddress(address);
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
