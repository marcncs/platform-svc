package com.winsafe.webservice.client.iam;

public class IamValidSoapProxy implements com.winsafe.webservice.client.iam.IamValidSoap {
  private String _endpoint = null;
  private com.winsafe.webservice.client.iam.IamValidSoap iamValidSoap = null;
  
  public IamValidSoapProxy() {
    _initIamValidSoapProxy();
  }
  
  public IamValidSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initIamValidSoapProxy();
  }
  
  private void _initIamValidSoapProxy() {
    try {
      iamValidSoap = (new com.winsafe.webservice.client.iam.IamValidLocator()).getIamValidSoap();
      if (iamValidSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)iamValidSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)iamValidSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (iamValidSoap != null)
      ((javax.xml.rpc.Stub)iamValidSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.winsafe.webservice.client.iam.IamValidSoap getIamValidSoap() {
    if (iamValidSoap == null)
      _initIamValidSoapProxy();
    return iamValidSoap;
  }
  
  public com.winsafe.webservice.client.iam.ADResult[] checkIdInGroupList(java.lang.String cwid, java.lang.String groupNames) throws java.rmi.RemoteException{
    if (iamValidSoap == null)
      _initIamValidSoapProxy();
    return iamValidSoap.checkIdInGroupList(cwid, groupNames);
  }
  
  public boolean checkIdInGroup(java.lang.String cwid, java.lang.String groupName) throws java.rmi.RemoteException{
    if (iamValidSoap == null)
      _initIamValidSoapProxy();
    return iamValidSoap.checkIdInGroup(cwid, groupName);
  }
  
  public java.lang.String[] getIdsFromGroup(java.lang.String groupName) throws java.rmi.RemoteException{
    if (iamValidSoap == null)
      _initIamValidSoapProxy();
    return iamValidSoap.getIdsFromGroup(groupName);
  }
  
  public java.lang.String[] getGroupsById(java.lang.String cwid) throws java.rmi.RemoteException{
    if (iamValidSoap == null)
      _initIamValidSoapProxy();
    return iamValidSoap.getGroupsById(cwid);
  }
  
  public boolean adLogin(java.lang.String cwid, java.lang.String password) throws java.rmi.RemoteException{
    if (iamValidSoap == null)
      _initIamValidSoapProxy();
    return iamValidSoap.adLogin(cwid, password);
  }
  
  
}