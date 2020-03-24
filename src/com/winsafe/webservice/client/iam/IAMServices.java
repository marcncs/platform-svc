package com.winsafe.webservice.client.iam;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import org.apache.axis.AxisProperties;

import com.winsafe.drp.util.HttpUtils;
import com.winsafe.drp.util.SysConfig;
import com.winsafe.drp.util.WfLogger;

public class IAMServices {
	public static String[] getIdsFromGroup(String groupName) throws RemoteException, ServiceException {
		IamValidLocator sl = new IamValidLocator();
		AxisProperties.setProperty("axis.socketSecureFactory", "com.winsafe.webservice.util.MySocketFactory");
		String[] ids = sl.getIamValidSoap().getIdsFromGroup(groupName);
		return ids;
	}
	
	public static String[] getIdsFromGroup2(String groupName) throws Exception {
		String url = SysConfig.getSysConfig().getProperty("iam.getIdsFromGroup");
		String apiKey = SysConfig.getSysConfig().getProperty("iam.api.key"); 
		Map<String, String> params = new HashMap<String, String>();
		params.put("groupName", groupName);
		String result = HttpUtils.getIdsFromGroup(url, params, apiKey);
		WfLogger.error("getIdsFromGroup2:"+result);
		result = result.replace("[", "").replace("]", "").replaceAll("\"", "");;
		String[] ids = result.split(",");
		return ids;
	}
}
