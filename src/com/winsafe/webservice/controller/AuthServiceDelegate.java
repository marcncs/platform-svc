package com.winsafe.webservice.controller;

import com.winsafe.drp.util.WfLogger; 
import com.winsafe.webservice.service.WSService;

@javax.jws.WebService(targetNamespace = "http://controller.webservice.winsafe.com/", serviceName = "AuthServiceService", portName = "AuthServicePort", wsdlLocation = "WEB-INF/wsdl/AuthServiceService.wsdl")
public class AuthServiceDelegate {

	com.winsafe.webservice.controller.AuthService authService = new com.winsafe.webservice.controller.AuthService();

	public boolean authenticate(String userName, String password) {
		return authService.authenticate(userName, password);
	}

}