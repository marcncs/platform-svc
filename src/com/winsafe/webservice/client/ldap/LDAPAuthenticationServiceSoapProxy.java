package com.winsafe.webservice.client.ldap;

public class LDAPAuthenticationServiceSoapProxy implements com.winsafe.webservice.client.ldap.LDAPAuthenticationServiceSoap {
  private String _endpoint = null;
  private com.winsafe.webservice.client.ldap.LDAPAuthenticationServiceSoap lDAPAuthenticationServiceSoap = null;
  
  public LDAPAuthenticationServiceSoapProxy() {
    _initLDAPAuthenticationServiceSoapProxy();
  }
  
  public LDAPAuthenticationServiceSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initLDAPAuthenticationServiceSoapProxy();
  }
  
  private void _initLDAPAuthenticationServiceSoapProxy() {
    try {
      lDAPAuthenticationServiceSoap = (new com.winsafe.webservice.client.ldap.LDAPAuthenticationServiceLocator()).getLDAPAuthenticationServiceSoap();
      if (lDAPAuthenticationServiceSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)lDAPAuthenticationServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)lDAPAuthenticationServiceSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (lDAPAuthenticationServiceSoap != null)
      ((javax.xml.rpc.Stub)lDAPAuthenticationServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.winsafe.webservice.client.ldap.LDAPAuthenticationServiceSoap getLDAPAuthenticationServiceSoap() {
    if (lDAPAuthenticationServiceSoap == null)
      _initLDAPAuthenticationServiceSoapProxy();
    return lDAPAuthenticationServiceSoap;
  }
  
  public boolean authenticateUserByLDAP(java.lang.String cwid, java.lang.String password) throws java.rmi.RemoteException{
    if (lDAPAuthenticationServiceSoap == null)
      _initLDAPAuthenticationServiceSoapProxy();
    return lDAPAuthenticationServiceSoap.authenticateUserByLDAP(cwid, password);
  }
  
  
}