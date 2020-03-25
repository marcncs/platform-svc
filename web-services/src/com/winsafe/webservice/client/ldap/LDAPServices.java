package com.winsafe.webservice.client.ldap;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.apache.axis.AxisProperties;

import com.winsafe.drp.util.HttpUtils;
import com.winsafe.drp.util.SysConfig;

public class LDAPServices {
	public static boolean authenticateUserByLDAP(String cwid, String password) throws RemoteException, ServiceException {
		LDAPAuthenticationServiceLocator sl = new LDAPAuthenticationServiceLocator();
		AxisProperties.setProperty("axis.socketSecureFactory", "com.winsafe.webservice.util.MySocketFactory");
		return sl.getLDAPAuthenticationServiceSoap().authenticateUserByLDAP(cwid, password);
	}
	
	public static boolean authenticateUserByAD(String cwid, String password) throws Exception {
		String adLoginUrl = SysConfig.getSysConfig().getProperty("iam.adLogin"); 
		String apiKey = SysConfig.getSysConfig().getProperty("iam.api.key"); 
		return HttpUtils.authenticateUser(adLoginUrl, cwid, password, apiKey);
	}
}
