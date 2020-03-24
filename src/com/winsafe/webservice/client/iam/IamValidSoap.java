/**
 * IamValidSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.winsafe.webservice.client.iam;

public interface IamValidSoap extends java.rmi.Remote {

    /**
     * 判断cwid是否在分组列表中<br/> groupNames:组名，多个以英文逗号隔开
     */
    public com.winsafe.webservice.client.iam.ADResult[] checkIdInGroupList(java.lang.String cwid, java.lang.String groupNames) throws java.rmi.RemoteException;

    /**
     * 检查cwid是否在分组中<br/> groupName:组名
     */
    public boolean checkIdInGroup(java.lang.String cwid, java.lang.String groupName) throws java.rmi.RemoteException;

    /**
     * 根据分组名称获取全部cwid<br/> groupName:组名
     */
    public java.lang.String[] getIdsFromGroup(java.lang.String groupName) throws java.rmi.RemoteException;

    /**
     * 根据cwid获取所在的分组<br/> cwid:
     */
    public java.lang.String[] getGroupsById(java.lang.String cwid) throws java.rmi.RemoteException;

    /**
     * AD登录验证<br/> cwid:
     */
    public boolean adLogin(java.lang.String cwid, java.lang.String password) throws java.rmi.RemoteException;
}
