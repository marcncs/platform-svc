package com.winsafe.webservice.controller;

import com.winsafe.drp.util.WfLogger; 
//import com.winsafe.drp.util.XmlUtil;
//import com.winsafe.webservice.pojo.Result;
import com.winsafe.webservice.service.WSService;

public class AuthService {
	
	private WSService wsService = new WSService();
	
	public boolean authenticate(String userName, String password) {
		try {
			return wsService.authenticate(userName, password);
		} catch (Exception e) { 
			WfLogger.error("ws用户验证错误", e);
//			Result result = new Result();
//			result.setReturnCode("0");
//			result.setReturnMsg("验证失败，系统异常，请联系管理员");
//			return XmlUtil.objectToXml(Result.class, result);
			return false;
		}
	}
}
