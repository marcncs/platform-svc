/**
 * LDAPAuthenticationServiceSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.winsafe.webservice.client.ldap;

public interface LDAPAuthenticationServiceSoap extends java.rmi.Remote {
    public boolean authenticateUserByLDAP(java.lang.String cwid, java.lang.String password) throws java.rmi.RemoteException;
}
