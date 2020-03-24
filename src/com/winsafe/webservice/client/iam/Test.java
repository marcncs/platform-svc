package com.winsafe.webservice.client.iam;

import org.apache.axis.AxisProperties;

public class Test {
	public static void main(String[] args) {
		IamValidLocator sl = new IamValidLocator();
		AxisProperties.setProperty("axis.socketSecureFactory", "com.winsafe.webservice.client.MySocketFactory");
//		System.out.println(sl.getIamValidSoap().checkIdInGroup(cwid, groupName));
	}
}
